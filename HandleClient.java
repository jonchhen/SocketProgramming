import java.io.*;
import java.net.*;

// Define the thread class for handling new connection
public class HandleClient implements Runnable {
    private Socket socket; // A connected socket

    /** Construct a thread */
    public HandleClient(Socket socket) {
        this.socket = socket;
    }

    /**
     * Run a thread This is where most of the code will go, we will handle all of
     * the logging, and compute the math problems sent from the clients here. We
     * will still need to do the client side stuff but most of the code will go
     * here, however we may create other classes or methods to make it more modular
     * and easier to read/maintain so I guess not all the code will be here, a lot
     * may be in functions the run function calls
     */
    public void run() {
        try {
            boolean cont = true;
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (cont) {

                String sentence = inFromClient.readLine();

                if (sentence != null) {

                    /**************/

                    // check if client wants to close the connection
                    char close = sentence.charAt(0);

                    if (close == 'x') {
                        System.out.println("Closing Connection...");

                        outToClient.println('x');
                        outToClient.flush();
                        socket.close();
                        System.out.println("Connection Closed");

                        cont = false;

                        return;
                    }
                    /**************/

                    String[] arr = sentence.split(" ");
                    if (checkFormat(arr)) {
                        int finalAnswer = calculate(arr);
                        outToClient.println(finalAnswer);
                        outToClient.flush();
                    } else {
                        // prompt user again to give valid input, pass the code 'r'
                        outToClient.println('r');
                        outToClient.flush();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculate(String[] arr) {

        int finalAnswer;

        int numOne = Integer.parseInt(arr[0]);
        String operator = arr[1].trim();
        int numTwo = Integer.parseInt(arr[2]);

        switch (operator) {
        case "+":
            finalAnswer = numOne + numTwo;
            break;
        case "-":
            finalAnswer = numOne - numTwo;
            break;
        case "/":
            finalAnswer = numOne / numTwo;
            break;
        case "*":
            finalAnswer = numOne * numTwo;
            break;
        default:
            finalAnswer = -99999999;
        }
        return finalAnswer;
    }

    // Validates user input
    private boolean checkFormat(String[] arr) {
        boolean valid = true;

        if (arr.length != 3) {
            valid = false;
        }

        try {
            Integer.parseInt(arr[0]);
            String operator = arr[1].trim();
            Integer.parseInt(arr[2]);

            if (!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/")) {
                valid = false;
            }

        } catch (Exception e) {
            valid = false;
        }

        return valid;
    }
}
