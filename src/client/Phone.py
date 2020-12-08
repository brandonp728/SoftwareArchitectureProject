from Device import Device
import time
import sys

class Phone(Device):
    def __init__(self, id, version):
        super().__init__(id, version)

    def main(self):
        

        print("Gonna connect this phone!")
        self.connectDevice()
        
        while True:
            print("Connected! Lets check for an update")
            while(not self.isUpdateAvailable()):
                print("doing my own things. no update available. id "+ self.id)
                time.sleep(3)
            
            print("I need one! Lets get a ticket!"+ self.id)
            while(not self.applyForTicket()):
                time.sleep(2)
            
            print("Got a ticket! Gonna apply it"+ self.id)
            if self.getUpdateFile():
                print("got the update. applying it"+ self.id)
                self.applyPatch()
                print("update done"+ self.id)
                self.confirmUpdate()
                print("Everything worked!"+ self.id)
                



if __name__ == "__main__":

    deviceId = sys.argv[1]

    phone = Phone(deviceId, 1)


    phone.main()
