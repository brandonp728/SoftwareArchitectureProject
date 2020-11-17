
class Queue {
    
    constructor(){
        this.deviceUpdateQueue = [];
        this.deviceMasterList = [];

        this.WAITING_CODE    = 101;
        this.DENIED_CODE     = 102;
        this.APPROVED_CODE   = 103;

        this.NEEDS_UPDATE_CODE  = 601;
        this.NO_UPDATE_CODE     = 602;

        this.ADDED_CODE      = 701;
        this.REJECTED_CODE   = 702;

        this.UPDATE_THRESHOLD_POLICY = 0.33
    }

    addDeviceToQueue(device) {
        let deviceLimit = this.deviceMasterList.length * this.UPDATE_THRESHOLD_POLICY;
        if (this.deviceInMasterList(device)) {
            if(this.deviceUpdateQueue.length < deviceLimit) {
                for(let i = 0; i < this.deviceUpdateQueue.length; i++) {
                    if(device === this.deviceUpdateQueue[i]["id"]) {
                        return true;
                    }
                }
                this.deviceUpdateQueue.push({"id":device});
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
    
    removeDeviceFromQueue(device) {
        if(this.deviceUpdateQueue.length == 0){
            console.log("Device Queue underflow");
        }
        else {
            for(let i = 0; i < this.deviceUpdateQueue.length; i++) {
                if(device === this.deviceUpdateQueue[i]["id"]) {
                    this.deviceInUpdateQueue.splice(i,1);
                    console.log(device + " was removed from update queue!");
                    break;
                }
            }
        }
    }
    
    getDeviceStatus(device) {
        return device["status"];
    }
    
    deviceInMasterList(device) {

        for(let i = 0; i < this.deviceMasterList.length; i++) {
            if(device === this.deviceMasterList[i]["id"]) {
                return true;
            }
        }
        return false;
    }

    deviceInUpdateQueue(device) {
        for(let i = 0; i < this.deviceUpdateQueue.length; i++) {
            if(device === this.deviceUpdateQueue[i]["id"]) {
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