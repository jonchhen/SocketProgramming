import java.io.*;
import java.util.Date;

public class Utilities {
    private static File file = new File("serverlog.txt"); 

    //write to log when client connects to server successfully
    public synchronized static boolean writeConnectionToLog(String message, long threadID, Date date, int portNum)
    {

        String[] arr = message.split(" ");
        StringBuilder logEntry = new StringBuilder(); 
        logEntry.append("Connection from client successful (client info below):\n");
        logEntry.append("Hostname: " + arr[0] + "\n");
        logEntry.append("IP address: " + arr[1] + "\n");
        logEntry.append("Port number: " + portNum + "\n");
        logEntry.append("Thread ID: " + threadID + "\n");
        logEntry.append("Connection Date: " + date + "\n\n");

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Failed to create file!");
            return false;
        }

        try (PrintWriter printToFile = new PrintWriter(new FileOutputStream(file,true))) 
        {
            printToFile.println(logEntry.toString());
        } 
        catch (IOException e) 
        {
            System.out.println("The program failed to write contents to the log!");
            return false;
        }
        return true;
    }
    
    //write to log when client successfully disconnects from server
    public synchronized static boolean writeSuccessfulDisconnectToLog(String hostName, String ip, long threadID, Date date, int portNum, long elapsedTime)
    {

        StringBuilder logEntry = new StringBuilder(); 
        logEntry.append("Client has disconnected from server (client info below):\n");
        logEntry.append("Hostname: " + hostName + "\n");
        logEntry.append("IP address: " + ip + "\n");
        logEntry.append("Port number: " + portNum + "\n");
        logEntry.append("Thread ID: " + threadID + "\n");
        logEntry.append("Disconnection Date: " + date + "\n");
        logEntry.append("Time Connected: " + elapsedTime + " seconds\n\n");

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Failed to create file!");
            return false;
        }

        try (PrintWriter printToFile = new PrintWriter(new FileOutputStream(file,true))) 
        {
            printToFile.println(logEntry.toString());
        } 
        catch (IOException e) 
        {
            System.out.println("The program failed to write contents to the log!");
            return false;
        }
        return true;
    }

    //write to log when client fails to follow connection protocol and failed connection occurs 
    public synchronized static boolean writeFailedConnectionToLog(String hostName, String ip, long threadID, Date date, int portNum)
    {

        StringBuilder logEntry = new StringBuilder(); 
        logEntry.append("Error: Client violated application protocol when attemting a connection to the server (client info below):\n");
        logEntry.append("Hostname: " + hostName + "\n");
        logEntry.append("IP address: " + ip + "\n");
        logEntry.append("Port number: " + portNum + "\n");
        logEntry.append("Thread ID: " + threadID + "\n");
        logEntry.append("Failed Connection Date: " + date + "\n\n");

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Failed to create file!");
            return false;
        }

        try (PrintWriter printToFile = new PrintWriter(new FileOutputStream(file,true))) 
        {
            printToFile.println(logEntry.toString());
        } 
        catch (IOException e) 
        {
            System.out.println("The program failed to write contents to the log!");
            return false;
        }
        return true;
    }

    //write to log when client makes a valid request
    public synchronized static boolean writeEventToLog(String ip, long threadID, Date date, int portNum, String request, int response)
    {

        StringBuilder logEntry = new StringBuilder(); 
        logEntry.append("Client Event (IP: " + ip + " thread ID: " + threadID + " port: " + portNum + "):\n");
        logEntry.append("Client request: " + request + "\n");
        logEntry.append("Server response: " + response + "\n");
        logEntry.append("Request Date: " + date + "\n\n");

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Failed to create file!");
            return false;
        }

        try (PrintWriter printToFile = new PrintWriter(new FileOutputStream(file,true))) 
        {
            printToFile.println(logEntry.toString());
        } 
        catch (IOException e) 
        {
            System.out.println("The program failed to write contents to the log!");
            return false;
        }
        return true;
    }

    //write to log when client makes invalid request
    public synchronized static boolean writeFailedEventToLog(String ip, long threadID, Date date, int portNum, String request, String response)
    {

        StringBuilder logEntry = new StringBuilder(); 
        logEntry.append("Unrecognizable Client Event (IP: " + ip + " thread ID: " + threadID + " port: " + portNum + "):\n");
        logEntry.append("Client request : " + request + "\n");
        logEntry.append("Server response: " + response + "\n");
        logEntry.append("Request Date: " + date + "\n\n");

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Failed to create file!");
            return false;
        }

        try (PrintWriter printToFile = new PrintWriter(new FileOutputStream(file,true))) 
        {
            printToFile.println(logEntry.toString());
        } 
        catch (IOException e) 
        {
            System.out.println("The program failed to write contents to the log!");
            return false;
        }
        return true;
    }
}