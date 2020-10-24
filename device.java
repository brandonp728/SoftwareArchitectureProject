// This is the Device to simulate iot devices.

// This is the client.

// Pseudo Code
// Connect to the socket, wait for a notification
// while alive
//     When notified of a patch, request ticket
//     wait for ticket
//     when provided a ticket, execute patch
//     provide status updates for server


import java.net.*;
import java.io.*;


public  class Device {

    // Socket Information
    private static final String IP = "";
    private static final int PORT = 3000;

    // Communication Channel
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Device() {
        this.startConnection();
    }

    public void startConnection() {
        try{
            this.socket =  new Socket(this.IP, this.PORT);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            out.println("hello server");
        }catch(IOException ioexcept) {
        }    
    }

    public void endConnection() {
        try{
            this.in.close();
            this.out.close();
            this.socket.close();
            out.println("bye server");
        }catch(IOException ioexcept) {
        }
    }

    // public abstract void performTask();
    // public abstract boolean update();

    public static void main(String[] args) throws Exception{
        Device device= new Device();
    }
}
