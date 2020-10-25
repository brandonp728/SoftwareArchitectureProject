/**
     * Pseudo code
     * 
     * This is the sever for the program.
     * Must connect to tcp sockets.
     * 
     * Manager notifies tickets that a patch is available.
     * Devices apply for a ticket from the manager.
     * Manager holds devices in a queue.
     * When manager decides another device may update it pops off the queue and is sent a notification.
     * Manger polls the activly updating devices for their status. (this is for fault detection as well)
     * Once the status of the updating device is completed a new device may be queue.
     * 
     * status code
     *      Server -> Client
     *      waiting permission (101)
     *      denied permision (102)
     *      approved permission (103)
     * 
     *      new patch available
     *      roll back
     * 
     *      Client -> Server
     *      inprogress (updating) (201)
     *      successful update   (202)
     *      failed update   (203)
     * 
     *   
     * The patch will be in the form of a file communicated over tcp sockets.
     * The file will contain attributes that need to be updated, and the individual devices
     * retrieve the attributes they care about. The files must simulate both successful and failed patches.
     * json formatting will be used.
     * 
     * 1 json per patch.
     * 
     * {
     *     "x": 11,
     *     "y": 20,
     *     "algorithm": "x + y"
     * }
     * 
     * {
     *     "x": 11,
     *     "y": 20,
     *     "algorithm": "x - y"
     * }
     */
class TicketManager {

    
    
    // this.testPatch =  {
    //          "x": 11,
    //          "y": 20,
    //          "algorithm": "x + y"
    //      }
    // parsedPatch = JSON.parse(this.testPatch); //recived by the server from the client 


    constructor(portNo) {
        //create a port that listens to the client
          this.portNo=port;

        this.WAITING_CODE = 101; //to be used in notifyPatchAvailable()
        this.DENIED_CODE = 102;
        this.APPROVED_CODE = 103;

        this.deviceQueue = new Queue();
        this.deviceArray = [];  
        this.app = express();
        this.port = 3055;

    }
             
    sendData(parsedPatch){
        //data paramter should be in JSON  
        //also sends to the client in JSON

        app.get('/async', async (req, res) => {
            parsePatch = await readUserAsync() //reading the patch in an async manner 
            res.send(parsedPatch)
          });

        //   app.get('/sync', (req, res) => {
        //     const user = readUserSync()
        //     res.send(user)
        //   });
        app.listen(port, () => console.log(`Ticket Manager Listening on port ${port}!`));     
    }
        
    getData(){
        //listens or recieves input in a JSON format 
        //return type parsed JSON 
        return this.parsedPatch; //to be replaced by the patch on the client side
    }

    applyForTicket(device) {
        // Deny when:
        //   if (in queue || updating queue )-  device is already in the queue
        //    &&  device is updating
        //      
        // Otherwise approve else{approve}

        // Check if device is currently updating OR device is in queue
        if(this.getDeviceStatus(device) == 201 || this.isInQueue(device)){
            return this.DENIED_CODE;
        } else {
            this.addDeviceToQueue(device);
            return this.APPROVED_CODE;
        }

    }

    notifyPatchAvailable() {
     //need to sync with the client    
    }

    addDeviceToQueue(device) {
        if(this.deviceQueue.getLength < 10) {
            this.deviceQueue.enqueue(device);
            this.deviceArray.push(device);
        }
        else {
            console.log("Queue overflow!")
        }
    }

    removeDeviceFromQueue() {
        device = this.deviceQueue.dequeue();
        this.getDeviceStatus(device);
        
        if(deviceQueue.length == 0){
            console.log("Device Queue underflow")
        }
        else{
            this.deviceArray.pop(device)
        }
    }

    getDeviceStatus(device) {
        return device.status;
    }

    isInQueue(device) {
        for(ticketedDevice in deviceArray) {
            if(device === ticketedDevice) {
                return true;
            }
        }
        return false;
    }

    displayDeviceArray() {
        for(i in this.deviceArray) {
            console.log(i);
        }
    } 

    main() {
        console.log("Hello!");
        // manager = new TicketManager(3055);

        // manager.sendData(this.parsedPatch);
        
        // this.removeDeviceFromQueue();
        // this.applyForTicket("Device1");
        // this.applyForTicket("Device2");
        // this.applyForTicket("Device3");
        // this.applyForTicket("Device4");
        // this.applyForTicket("Device5");
        // this.applyForTicket("Device1");
        // this.removeDeviceFromQueue();
        // this.removeDeviceFromQueue();
        // this.removeDeviceFromQueue();
        // this.displayDeviceArray();
    }
}

// let newServer = new TicketManager(3000);



