package client;

// TODO: Add DeviceManager to start the device processes
//
// Connect device to server
    // Poll for updates - need edge point for general status updates
    // When update exists, apply for ticket
    // When provided ticket, request patch diff
    // when handed patch diff, implement patch
    // Push status update after patch implemented.
    // Restart process

// File Name GreetingServer.java
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.*;
import org.json.*;

import BsPatch;

public abstract class Device {

    // Client -> Server Codes
    private final int OPERATING = 200;
    private final int WAITING = 201;
    private final int SUCCESFUL_UPDATE = 202;
    private final int FAILED_UPDATE = 203;

    // HTTP Client Information
    private static final String HOST = "http://localhost:4000";
    

    // Communication Channel
    private String id;
    private boolean updateAvilable;
    private String version = "";
    private String newVersion = "";
    private HttpClient client;

    public Device(String id, String v) {
        this.id = id;
        this.updateAvilable = false;
        this.version = v; 
        this.client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    }

    public boolean connectDevice() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Device.HOST + "/registerDevice")).header("id", this.id).build();
        try {
            HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
            if(response.statusCode() == 701) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean applyForTicket() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Device.HOST + "/applyForTicket")).header("id", this.id).build();
        boolean approved = false;
        try {
            HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
            while(this.updateAvilable && response.statusCode() != 103) {
                response = this.client.send(request, BodyHandlers.ofString());
                approved = (response.statusCode() == 103);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approved;
    }

    // Is this necessary?
    public void disconnectDevice() {

    }

    public boolean isUpdateAvailable(){
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(Device.HOST + "/updateAvailable"))
            .header("id", this.id)
            .header("version", this.version)
            .build();
        try {
            HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
            if(response.statusCode() == 601) {
                this.changeUpdateAvailability(true);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getUpdateFile(){
        if(this.updateAvailable) {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(Device.HOST + "/needUpdate"))
            .build();
            try {
                HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
                HttpDownloadUtility.downloadFile(response.uri, "resources");
                System.out.println("Downloaded!");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You don't need an update.");
            return false;
        }
        return false;
    }

    public boolean applyPatch() {
        RandomAccessFile oldFile = new RandomAccessFile("resources/react-native-zip-archive-5.0.1.zip");
        OutputStream newFile = new FileOutputStream("resources/react-native-zip-archive-5.0.6.zip");
        InputStream patchFile = new FileInputStream("resources/update.patch");
        try {
            BsPatch.applyPatch(oldFile, newFile, patchFile);
            System.out.println("File patched!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void changeUpdateAvailability(boolean available) {
        this.updateAvilable = available;
    }

    // public void handleConnection() {
    //     JSONObject toDeviceJSON, toServerJSON = null;
    //     int code = this.OPERATING;

    //     // Main Loop - Life of Process
    //     while(true) {
    //         this.performTask();
    //         toDeviceJSON = readFromServer();
    //         switch(this.getCommand(toDeviceJSON)) {
    //             case 101:   // Patch Available
    //                 code = this.WAITING;
    //                 break;
    //             case 102:   // Wait for Permision
    //                 code = this.WAITING;
    //                 break;
    //             case 103:   // Denied Permssion
    //                 code = this.OPERATING;
    //                 break;
    //             case 104:   // Approved Permission
    //                 if(this.update(toDeviceJSON.getJSONObject("patch"))) {
    //                     code = this.SUCCESFUL_UPDATE;
    //                 } else {
    //                     code = this.FAILED_UPDATE;
    //                 }
    //                 break;
    //         }

    //         // Send Message to Server
    //         toServerJSON = new JSONObject();
    //         toServerJSON.put("code", code);
    //         this.writeToServer(toServerJSON);
    //     }
    // }

    // private JSONObject readFromServer() {
    //     try {
    //         HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Device.HOST + "/getUpdateFile")).header("id", this.id).build();
    //         HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
    //         return new JSONObject(response.body());
    //     } catch (Exception e) {

    //     }
    //     return null;
    // }

    // private void writeToServer(JSONObject json) {
        
    // }

    // private int getCommand(JSONObject json) {
    //     if(json == null) {
    //         return -1;
    //     }
    //     return (Integer) json.get("status");
    // }

    protected abstract void performTask();
    protected abstract boolean update(JSONObject patch);
}
