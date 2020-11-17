// var TicketManager = require('./TicketManager.js');
// TicketManager.main();
// import './deviceQueue.js';

// import './Queue.js'

const fs = require('fs');
const path = require('path');
const bsdiff = require('bsdiff-nodejs');

var Queue = require('./Queue.js');
let deviceQueue = new Queue()
// let globalVersion = "1.0.0";
let currentFirmwareFilePath = "react-native-zip-archive-5.0.1.zip"
let patchFilesPaths = {}

let currentVersion = 1;




const express = require('express');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.raw());

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

    // Check if device is in queue or is currently updating
// If true, do not give it a ticket
// Otherwise give it a ticket
app.post('/applyForTicket', function(req,res){
    // console.log(req);

    id = req.body.id;
    version = req.body.version;

    if(version == currentVersion){
        res.json({'code':deviceQueue.DENIED_CODE});
    }else {
        if(deviceQueue.addDeviceToQueue(id)) {
            res.json({'code':deviceQueue.APPROVED_CODE});
        }else {
            res.json({'code':deviceQueue.DENIED_CODE});
        }
    }
    
});


app.post('/registerDevice', function(req,res){
    var id = req.body.id

    if(!deviceQueue.deviceInMasterList(id)) {
        // add device to master list
        deviceQueue.addDeviceToMasterList(id);
        res.json({'code':deviceQueue.ADDED_CODE});
    } else {
        res.json({"code":deviceQueue.REJECTED_CODE});
    }
});


let hardUpdateFile = {'x':1, 'y': 2, 'c':3, 'k':4}
let hardBadUpdateFile = {'k':1, 'f': 2, 'x':3, 'u':4}


// app.get('/getUpdateFile', function(req,res){
//     console.log(deviceQueue.deviceMasterList);
//     console.log(deviceQueue.deviceUpdateQueue);
//     if (req.headers.id) {
//         if (deviceQueue.deviceInUpdateQueue(req.headers.id)) {
//             res.json(hardUpdateFile);
//         }else {
//             res.json({'code':deviceQueue.REJECTED_CODE});
//         }
//     }
// });


app.get('/getUpdateFile', function(req,res){
    console.log(deviceQueue.deviceMasterList);
    console.log(deviceQueue.deviceUpdateQueue);

    var id = req.body.id

    if (id) {
        if (deviceQueue.deviceInUpdateQueue(id)) {
            let deviceVersion = req.body.version;
            let patchFileName = deviceVersion+"-"+currentVersion+'-update.patch';

            if (patchFileName in patchFilesPaths) {
                console.log("found existing patch. sending that");
                res.download(patchFilesPaths[patchFileName])
            }else {
                console.log("generating patch file");
                let oldFile = path.join(__dirname, 'resources/' + deviceVersion + '.zip');
                let newFile = path.join(__dirname, 'resources/' + currentVersion + '.zip');
                
                let patchFileName = deviceVersion+"-"+currentVersion+'-update.patch';
                let  patchFile = path.join(__dirname, 'resources/'+ patchFileName);
                patchFilesPaths[patchFileName] = patchFile

                bsdiff.diff(oldFile, newFile, patchFile, function (result) {
                    console.log('diff:' + String(result).padStart(4) + '%');
                });

                res.download(patchFile);
            }
        }else {
            res.json({'code':deviceQueue.REJECTED_CODE});
        }
    }

    


});

app.put('/deviceUpdated', function(req,res){
    deviceQueue.removeDeviceFromQueue(req.body.id);
});

app.get('/updateAvailable', function(req,res){
    let id = req.body.id;
    let version = req.body.version;
    if(version != currentVersion) {
        res.json({'code':deviceQueue.NEEDS_UPDATE_CODE});
    } else {
        res.json({'code':deviceQueue.NO_UPDATE_CODE});
    }
});



app.post('/needUpdate', function(req,res) {
    let newVersion = req.body.version;

    currentVersion = newVersion;
    res.json({'code':701});
})



app.listen(4000);