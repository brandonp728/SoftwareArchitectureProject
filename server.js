// var TicketManager = require('./TicketManager.js');
// TicketManager.main();
// import './deviceQueue.js';



var deviceQueue = require('./deviceQueue.js');


// import {* from './js'

var express = require('express');
var app = express();

app.get('/', function(req, res){
   res.send("Hello world!");
});

let tempvar = 7

let updateFile = {'f':1, 'u': 2, 'c':3, 'k':4}

app.get('/applyForTicket', function(req,res){
    // Check if device is in queue or is currently updating
    // If true, do not give it a ticket
    // Otherwise give it a ticket
    if(getDeviceStatus(device) === 201 || isInQueue(device)){
        return DENIED_CODE;
    } else {
        addDeviceToQueue(device);
        return APPROVED_CODE;
    }
    addDeviceToQueue(req.headers.id)
    res.json({'code':103});
});


app.get('/registerDevice', function(req,res){
    if(!deviceInMasterList(req.headers.id)) {
        // add device to master list
        addDeviceToMasterList(req.headers.id);
        res.json({'code':701});
    } else {
        res.json({"code":702});
    }
    
});


let updateFile = {'x':1, 'y': 2, 'c':3, 'k':4}
let badUpdateFile = {'f':1, 'u': 2, 'x':3, 'k':4}
app.get('/getUpdateFile', function(req,res){
    // res.send("Here is the damn update" + tempvar)
    let goodOrBad = Math.random() %2;
    if (goodOrBad == 1) {
        res.json(updateFile);
    }else {
        res.json(badUpdateFile);
    }
    tempvar += 1
});


app.listen(4000);