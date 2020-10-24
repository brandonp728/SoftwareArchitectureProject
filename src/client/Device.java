package client;

// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import org.json.*;

public abstract class Device {

    // Client -> Server Codes
    private final int OPERATING = 200;
    private final int WAITING = 201;
    private final int SUCCESFUL_UPDATE = 202;
    private final int FAILED_UPDATE = 203;

    // Socket Information
    private static final String IP = "";
    private static final int PORT = 2000;

    // Communication Channel
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection() {
        try {
            this.socket = new Socket(Device.IP, Device.PORT);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ioexcept) {
        }
    }

    public void endConnection() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException ioexcept) {
        }
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
            String x = this.in.readLine();
            return new JSONObject(x.substring(2, x.length()));
        } catch (Exception e) {

        }
        return null;
    }

    private void writeToServer(JSONObject json) {
        this.out.println(json.toString());
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
