from Device import Device
import time

class Phone(Device):
    def __init__(self, id, version):
        super().__init__(id, version)

    def main(self):
        print("Gonna connect this phone!")
        self.connectDevice()
        
        print("Connected! Lets check for an update")
        while(not self.isUpdateAvailable()):
            time.sleep(2)
        
        print("I need one! Lets get a ticket!")
        while(not self.applyForTicket()):
            time.sleep(2)
        
        print("Got a ticket! Gonna apply it")
        if self.getUpdateFile():
            self.applyPatch()
            print("Everything worked!")


if __name__ == "__main__":
    phone = Phone(5, 1)
    phone.main()
