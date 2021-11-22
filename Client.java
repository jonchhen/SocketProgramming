import java.io.*;
import java.net.*;

import javax.print.event.PrintEvent;

public class Client{

    public static void main(String[] args) throws IOException{
    
        //Create the socket connection
        Socket socket = new Socket("localhost", 3000);
        boolean cont = true;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while(cont){
            System.out.print("Please enter a simple math problem for the server to solve (ex. 2 + 2) or Enter 'x' to close the connection: ");
            String userInput = inFromUser.readLine();

            outToServer.println(userInput);
            outToServer.flush();
            String finalAnswer = inFromServer.readLine();
	
	/**************/	
	//Check if connection is closed by the server
	char close = finalAnswer.charAt(0);

	if( close == 'x'){
	System.out.println("Connection Closed");
	System.exit(0);
	}
	/**************/	
            System.out.println("Answer: " + finalAnswer);
        }
        socket.close();


    }

}