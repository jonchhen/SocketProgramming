# SocketProgramming
Socket Programming done with JAVA. Demonstrate the ability to Client Server interaction. Client sends information through a socket using a port number, Server listens to that port number, when connection is established, server designates a different socket to deal with that Client. Uses TCP as a connection protocol.

In this program, Server listens for connection to be made. Once there is a connection. The client can send basic math calculation eg (2 + 2) and the server responds with the answer 4. 

There can be multiple clients connected to one server. For each client, a separate thread is made. 

Each client can send multiple request, and also tell the server when to stop the connection. 

Information about the files included:
Client.java: Has client code
Server.java: Has server code
HandleClient.java: Handles the math part of the program / deals with client
Makefile: used to compile the program


To use the program: 
      - Download the files
      - Open the terminal on the directory where the files are.
      - type 'make' command to run the Makefile, this compiles all the required files
      - run the Server first, by typing the command: java Server
      - open another terminal at same location, run the Client, by typing the command: java Client
      - for multiple client open multiple terminals and run the above step
      - from the client window, type the math command, eg: 2 + 2, the cliend gets 4 as a response
      - if client wants to exit/close the connection, type 'x' and it closes the connection between that client and the server.
