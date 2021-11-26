import java.io.*;
import java.net.*;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {

        // Create the socket connection
        Socket socket = new Socket("localhost", 3000);

        //get client information
        InetAddress host = InetAddress.getLocalHost();
        String hostName = host.getHostName();
        String hostIP = host.getHostAddress();
        String clientInfo = hostName + " " + hostIP + " " + socket.getPort();

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //client sends client info (host name and IP address and port number) to server to satisfy the protocol (handshake)
        outToServer.println(clientInfo);
        outToServer.flush();

        //the server will let the client know if it satisfied the protocol and if it can resume 
        //communicating with the server
        String response = inFromServer.readLine();
        if(response.equals("failure!")){

            System.out.println("Error: The client information was invalid and the TCP connection will be closed...");
            System.out.println("Connection Closed");

        }
        //if the protocol was satified the code in the else block will execute
        else{

            boolean valid = true;
            while (valid) {
                System.out.println("**********\nInput format -> 2 operands and 1 operator seperated by space (ex. 2 + 2)");
                System.out.print(
                        "Please enter a simple math problem for the server to solve\n or Enter 'x' to close the connection: ");
                String userInput = inFromUser.readLine();

                outToServer.println(userInput);
                outToServer.flush();
                response = inFromServer.readLine();

                // Check if connection is closed by the server
                if (response.toLowerCase().equals("x")) {
                    System.out.println("Connection Closed");
                    valid = false;
                    // "Invalid Format" message means user input invalid. Ask question again
                } else if (response.equals("Invalid Format")) {
                    System.out.println("\nError!!! Wrong input format!!!\n");
                } else
                    System.out.println("\nAnswer: " + response + "\n");
            }

        }

        socket.close();
    }

}