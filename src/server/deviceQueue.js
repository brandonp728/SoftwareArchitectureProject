

let deviceUpdateQueue = [];
let deviceMasterList = [];

let WAITING_CODE    = 101;
let DENIED_CODE     = 102;
let APPROVED_CODE   = 103;

function addDeviceToQueue(device) {
    if(this.deviceUpdateQueue.getLength < 10) {
        this.deviceUpdateQueue.push({"id":device,"status":WAITING_CODE});
    }
    else {
        console.log("Queue overflow!")
    }
}

function removeDeviceFromQueue() {
    if(deviceUpdateQueue.length == 0){
        console.log("Device Queue underflow")
    }
    else{
        device = this.deviceUpdateQueue.pop();
        this.getDeviceStatus(device);
    }
}

function getDeviceStatus(device) {
    return device["status"];
}

function deviceInMasterList(device) {
    for(ticketedDevice in deviceMasterList) {
        if(device === ticketedDevice) {
            return true;
        }
    }
    return false;
}

function addDeviceToMasterList(device) {
    deviceQueue.deviceMasterList.push({"id":device,"status":0});
}
