package client;

// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import org.json.*;

public abstract class Device {
    
    // Socket Information
    private static final String IP = "127.0.0.1";
    private static final int PORT = 3355;

    // Communication Channel
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection() {
        try{
            this.socket =  new Socket(Device.IP, Device.PORT);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }catch(IOException ioexcept) {
        }    
    }

    public void endConnection() {
        try{
            this.in.close();
            this.out.close();
            this.socket.close();
        }catch(IOException ioexcept) {
        }
    }

    public void handleConnection() {
        JSONObject json;
        while(true) {
            this.performTask();
            json = readFromServer();
            switch(this.getCommand(json)) {
                case 101:
                    break;
                case 102:
                    break;
            }
        }
    }

    private JSONObject readFromServer() {
        return null;
    }

    private void writeToServer(JSONObject json) {

    }

    private int getCommand(JSONObject json) {
        return 0;
    }

    protected abstract void performTask();
    protected abstract boolean update(JSONObject patch);
}
