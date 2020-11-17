import threading
from client.Device import Device
from client.Phone import Phone

class DeviceManager:

    def main(self):
        devices = []
        for i in range(1, 5):
            devices.append(Phone(i, 1))

        for d in devices:
            x = threading.Thread(target=d.__main__, args=())
            x.start()

        while(True):
            pass

    if __name__ == "__main__":
        main()
