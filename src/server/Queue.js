
class Queue {
    
    constructor(){
        this.deviceUpdateQueue = [];
        this.deviceMasterList = [];

        this.WAITING_CODE    = 101;
        this.DENIED_CODE     = 102;
        this.APPROVED_CODE   = 103;
    }

    addDeviceToQueue(device) {
        if (this.deviceInMasterList(device)) {
            if(this.deviceUpdateQueue.length < 10) {
                for(let i = 0; i < this.deviceUpdateQueue.length; i++) {
                    if(device === this.deviceUpdateQueue[i].id) {
                        return true;
                    }
                }
                this.deviceUpdateQueue.push({"id":device,"status":this.WAITING_CODE});
                return true;
            }
            else {
                console.log("Queue overflow!")
                return false;
            }
        }else {
            console.log("Device not registered!")
            return false
        }
        
    }
    
    removeDeviceFromQueue() {
        if(this.deviceUpdateQueue.length == 0){
            console.log("Device Queue underflow")
        }
        else{
            device = this.deviceUpdateQueue.pop();
            this.getDeviceStatus(device);
        }
    }
    
    getDeviceStatus(device) {
        return device["status"];
    }
    
    deviceInMasterList(device) {

        for(let i = 0; i < this.deviceMasterList.length; i++) {
            if(device === this.deviceMasterList[i].id) {
                return true;
            }
        }
        return false;
    }
    
    addDeviceToMasterList(device) {
        this.deviceMasterList.push({"id":device,"status":0});
    }

}
module.exports = Queue;