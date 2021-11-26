import java.io.*;
import java.net.*;

/**
 * This file won't need to be changed much, most of the code will be in the run
 * function in the HandleClient.java file
 */
public class Server {
    public static void main(String[] args) throws IOException {

        new Thread(() -> {
            try {
                // Create the server socket connection
                ServerSocket serverSocket = new ServerSocket(3000);

                //new threads are created here when new clients connect
                while (true) {
                    Socket socket = serverSocket.accept(); // Connect to a client
                    HandleClient client = new HandleClient(socket);
                    Thread thread = new Thread(client);
                    thread.start();
                    client.setThreadID(thread.getId());
                    System.out.println("New Thread Started");
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }
}