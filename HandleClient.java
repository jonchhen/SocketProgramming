import java.io.*;
import java.net.*;
import java.util.Date;

// Define the thread class for handling new connection
public class HandleClient implements Runnable {
    private Socket socket; // A connected socket
    private long threadID, startTime;
    private String ipAddress, hostName;

    /** Construct a thread */
    public HandleClient(Socket socket) {
        this.socket = socket;
    }

    //run method gets executed when new thread starts
    public void run() {
        try {
            //set timer to measure how long client was connected when connection is closed
            setTimer();

            //get date of initial connection
            Date date = new Date();
            boolean cont = true;
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            //upon the connection the client must push its hostname and IP address to fulfill the protocol (handshake)
            //the server will make sure the values are not null and that the IP address is valid (or at least the format)
            String request = inFromClient.readLine();

            //check if handshake protocol is met, otherwise close the connection and report error to log
            if(performHandshake(request)){

                //after valid handshake we can set the IP address and hostname of the client
                setIPAndHostname(request);

                //print basic message in console, log details
                System.out.println("Connection successful from client: " + request);
                Utilities.writeConnectionToLog(request, threadID, date, socket.getPort());
                outToClient.println("success!");
                outToClient.flush();

                while (cont) {

                    request = inFromClient.readLine();
    
                    if (request != null) {
    
                        /**************/
    
                        String[] arr = request.split(" ");
                        // check if client wants to close the connection
                        if (request.equals("x")) {
                            System.out.println("Closing Connection...");
    
                            outToClient.println("x");
                            outToClient.flush();
                            socket.close();
                            long elapsedTime = (System.nanoTime() - startTime) / 1000000000;
                            System.out.println("Connection Closed");
                            Utilities.writeSuccessfulDisconnectToLog(hostName, ipAddress, threadID, new Date(), socket.getPort(), elapsedTime);
                            cont = false;
    
                            return;
                        }
                        /**************/
                        //check request format, if valid calculate the solution and send it to the client, and log the event
                        else if (checkFormat(arr)) {
                            int response = calculate(arr);
                            outToClient.println(response);
                            outToClient.flush();
                            Utilities.writeEventToLog(ipAddress, threadID, new Date(), socket.getPort(), request, response);
                        } 
                        //if the request is not valid, let the client know and log the event
                        else {
                            // prompt user again to give valid input, pass the message "Invalid Format"
                            String response = "Invalid Format";
                            outToClient.println(response);
                            outToClient.flush();
                            Utilities.writeFailedEventToLog(ipAddress, threadID, new Date(), socket.getPort(), request, response);
                        }
                    }
                }
            }
            //if the client violates the application protocol, close the connection and log the event
            else{

                System.out.println("Closing Connection...");

                outToClient.println("failure!");
                outToClient.flush();
                socket.close();
                Utilities.writeFailedConnectionToLog(hostName, ipAddress, threadID, new Date(), socket.getPort());
                System.out.println("Connection Closed");

            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    //this method calculates the clients problem
    private int calculate(String[] arr) {

        int response;

        int numOne = Integer.parseInt(arr[0]);
        String operator = arr[1].trim();
        int numTwo = Integer.parseInt(arr[2]);

        switch (operator) {
        case "+":
            response = numOne + numTwo;
            break;
        case "-":
            response = numOne - numTwo;
            break;
        case "/":
            response = numOne / numTwo;
            break;
        case "*":
            response = numOne * numTwo;
            break;
        default:
            response = -99999999;
        }
        return response;
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

    //this is basically the application protocol which makes sure the client sends its hostname, IP, and port number
    //in a specific format that must be validated by the server before the client can continue communicating with the server
    //REQUIRED FORMAT: "hostname IP portnumber" 
    private boolean performHandshake(String message){

        String[] infoArray = message.split(" ");
        boolean connectionValid = true;

        if(infoArray.length == 3){

            if(infoArray[0] == null || infoArray[0] == "") connectionValid = false;

            String ipAddy = infoArray[1];
            String[] ipArray = ipAddy.split("\\.");

            if(ipArray.length == 4){

                for(int i = 0; i < 4; i++){

                    String str = ipArray[i].trim();
                    try{

                        Integer.parseInt(str);

                    }catch(NumberFormatException e){

                        connectionValid = false;

                    }
                }
            }
            else connectionValid = false;

            String str2 = infoArray[2].trim();
            try{
                Integer.parseInt(str2);

            }catch(NumberFormatException e){

                connectionValid = false;
            }
        }
        else connectionValid = false;
        return connectionValid;
    }

    private void setTimer(){

        this.startTime = System.nanoTime();

    }

    private void setIPAndHostname(String request){

        String[] arr = request.split(" ");
        this.hostName = arr[0].trim();
        this.ipAddress = arr[1].trim();

    }

    public void setThreadID(long ID){

        this.threadID = ID;

    }
}