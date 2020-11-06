// var TicketManager = require('./TicketManager.js');
// TicketManager.main();
// import './deviceQueue.js';

// import './Queue.js'

var Queue = require('./Queue.js');
let deviceQueue = new Queue()

var express = require('express');
var app = express();

app.get('/', function(req, res){
   res.send("Hello world!");
});

let tempvar = 7

let updateFile = {'w':1, 'h': 2, 'a':3, 't':4}


// Routes Needed
// All the routes expect a device id to be submitted
// POST Register & Unregister (way to detect device has disconnected)
    // What devices exist on the network. Device submits its id.
    // Device Manager will set the device id
// POST/GET A way to apply for a ticket
    // Devices continously poll for a ticket until it recieves it (submit id, waiting for ticket response)
// GET Requesting bspatch diff
    // retrieve patch diff (we are only keeping track of the current and newer versions on the server side).
    // return bspath diff
// POST bspatch result/status (did it work or not)
    // Submit device id and status code

app.get('/applyForTicket', function(req,res){
    // Check if device is in queue or is currently updating
    // If true, do not give it a ticket
    // Otherwise give it a ticket

    if(deviceQueue.addDeviceToQueue(req.headers.id)) {
        res.json({'code':deviceQueue.APPROVED_CODE});
    }else {
        res.json({'code':deviceQueue.DENIED_CODE});
    }

    // if(getDeviceStatus(device) === 201 || isInQueue(device)){
    //     return DENIED_CODE;
    // } else {
    //     addDeviceToQueue(device);
    //     return APPROVED_CODE;
    // }
    // addDeviceToQueue(req.headers.id)
    // 
});


app.get('/registerDevice', function(req,res){
    if(!deviceQueue.deviceInMasterList(req.headers.id)) {
        // add device to master list
        deviceQueue.addDeviceToMasterList(req.headers.id);
        res.json({'code':701});
    } else {
        res.json({"code":702});
    }
});


let hardUpdateFile = {'x':1, 'y': 2, 'c':3, 'k':4}
let hardBadUpdateFile = {'k':1, 'f': 2, 'x':3, 'u':4}
app.get('/getUpdateFile', function(req,res){
    // res.send("Here is the damn update" + tempvar)
    let goodOrBad = Math.random() %2;
    if (goodOrBad == 1) {
        res.json(hardUpdateFile);
    }else {
        res.json(hardBadUpdateFile);
    }
    tempvar += 1
});

app.get('/deviceUpdated', function(req,res){

});


app.listen(4000);