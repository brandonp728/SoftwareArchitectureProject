import java.net.*;

import java.io.*;

public class DummyServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void start(int port) {
        try{
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
        } catch(IOException ioexcept) {
        }

        while(true) {
            try{
                String greeting = this.in.readLine();
                if ("hello server".equals(greeting)) {
                    out.println("hello client");
                }
                else {
                    out.println("unrecognised greeting");
                }
            } catch(IOException ioexcept) {
            }
        }
    }

    public void stop() {
        try{
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch(IOException ioexcept) {
        }
    }
    public static void main(String[] args) {
        DummyServer server=new DummyServer();
        server.start(3000);
    }
}
