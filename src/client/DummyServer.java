package client;

// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import org.json.*;

public class DummyServer extends Thread {
   private ServerSocket serverSocket;
   
   private JSONObject createJSON() {
      JSONObject result = new JSONObject();
      JSONObject patch = new JSONObject();
      result.put("status", 104);
      patch.put("x", 3);
      patch.put("algorithm", "x + y");
      result.put("patch", patch);
      return result;
   }

   public DummyServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
    //   serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            // System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(this.createJSON().toString());
            // out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
            //    + "\nGoodbye!");
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = 2000; //Integer.parseInt(args[0]);
      try {
         Thread t = new DummyServer(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
