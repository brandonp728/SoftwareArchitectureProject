import requests
import time
import bsdiff4 as bsdiff

class Device:
    OPERATING = 200
    WAITING = 201
    SUCCESFUL_UPDATE = 202
    FAILED_UPDATE = 203

    HOST = "http://localhost:4000"

    def __init__(self, id, version):
        self.id = id
        self.updateAvilable = False
        self.version = version

    def connectDevice(self):
        reg_dev = self.HOST + '/registerDevice'
        req = requests.post(reg_dev, data={'id': self.id, 'version':self.version})
        if req.json()['code'] == 701:
            return True
        else:
            return False


    def applyForTicket(self):
        apply_ticket = self.HOST + "/applyForTicket"
        if(self.isUpdateAvailable()):
            req = requests.post(apply_ticket, data={'id': self.id, 'version':self.version})
            if(req.json()['code'] == 103):
                return True
        return False  

    def isUpdateAvailable(self):
        update_available = self.HOST + "/updateAvailable"
        req = requests.get(update_available, data={'id': self.id, 'version':self.version})
        if req.json()['code'] == 601:
            self.updateAvilable = True
            return True
        else:
            return False

    def getUpdateFile(self):
        update_file = self.HOST + "/getUpdateFile"
        req = requests.get(update_file, data={'id': self.id, 'version':self.version})
        try:
            if req.json()['code'] == 702:
                return False
        except:
            open('update.patch', 'wb').write(req.content)
            return True

    def applyPatch(self):
        # Three params: src_path, dst_path, patch_path
        # src_path      -> file to be patched
        # dst_path      -> new patched file
        # patch_path    -> patch to be applied
        try: 
            bsdiff.file_patch('resources/' + self.id + '/1.zip', 'resources/' + self.id + '/2.zip', 'resources/' + self.id + '/update.patch')
            # All done!
            return True
        except:
            print("Something went wrong in the application of the patch!")
            return False

