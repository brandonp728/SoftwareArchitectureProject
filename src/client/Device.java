package client;

// File Name GreetingServer.java
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.*;
import org.json.*;

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
    private HttpClient client;

    public Device(String id) {
        this.id = id;
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

        }
        return false;
    }

    public void disconnectDevice() {

    }

    public void handleConnection() {
        JSONObject toDeviceJSON, toServerJSON = null;
        int code = this.OPERATING;

        // Main Loop - Life of Process
        while(true) {
            this.performTask();
            toDeviceJSON = readFromServer();
            switch(this.getCommand(toDeviceJSON)) {
                case 101:   // Patch Available
                    code = this.WAITING;
                    break;
                case 102:   // Wait for Permision
                    code = this.WAITING;
                    break;
                case 103:   // Denied Permssion
                    code = this.OPERATING;
                    break;
                case 104:   // Approved Permission
                    if(this.update(toDeviceJSON.getJSONObject("patch"))) {
                        code = this.SUCCESFUL_UPDATE;
                    } else {
                        code = this.FAILED_UPDATE;
                    }
                    break;
            }

            // Send Message to Server
            toServerJSON = new JSONObject();
            toServerJSON.put("code", code);
            this.writeToServer(toServerJSON);
        }
    }

    private JSONObject readFromServer() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Device.HOST + "/getUpdateFile")).header("id", this.id).build();
            HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {

        }
        return null;
    }

    private void writeToServer(JSONObject json) {
        
    }

    private int getCommand(JSONObject json) {
        if(json == null) {
            return -1;
        }
        return (Integer) json.get("status");
    }

    protected abstract void performTask();
    protected abstract boolean update(JSONObject patch);
}
