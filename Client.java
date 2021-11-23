import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        // Create the socket connection
        Socket socket = new Socket("localhost", 3000);
        boolean cont = true, valid = true;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (cont && valid) {
            System.out.println("**********\nInput format -> 2 operands and 1 operator seperated by space (ex. 2 + 2)");
            System.out.print(
                    "Please enter a simple math problem for the server to solve\n or Enter 'x' to close the connection: ");
            String userInput = inFromUser.readLine();

            outToServer.println(userInput);
            outToServer.flush();
            String finalAnswer = inFromServer.readLine();

            // Check if connection is closed by the server
            char out = finalAnswer.charAt(0);

            if (out == 'x') {
                System.out.println("Connection Closed");
                valid = false;
                // 'r' code means user input invalid. Ask question again
            } else if (out == 'r') {
                System.out.println("\nError!!! Wrong input format!!!\n");
            } else
                System.out.println("\nAnswer: " + finalAnswer+"\n");
        }
        socket.close();

    }

}