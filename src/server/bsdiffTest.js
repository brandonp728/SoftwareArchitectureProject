const fs = require('fs');
const path = require('path');

const bsdiff = require('bsdiff-nodejs');


const oldFile = path.join(__dirname, 'resources/react-native-zip-archive-5.0.1.zip');
const newFile = path.join(__dirname, 'resources/react-native-zip-archive-5.0.6.zip');
const patchFile = path.join(__dirname, 'resources/react.patch');
// const generatedFile = path.join(__dirname, 'resources/react-generated.zip');

async function asyncCall() {
    await bsdiff.diff(oldFile, newFile, patchFile, function (result) {
        console.log('diff:' + String(result).padStart(4) + '%');
    });

    await bsdiff.patch(oldFile, generatedFile, patchFile, function (result) {
        console.log('patch:' + String(result).padStart(4) + '%');
    });
}
  
asyncCall();