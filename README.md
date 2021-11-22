# SocketProgramming
Socket Programming done with JAVA. Demonstrate the ability to Client Server interaction. Client sends information through a socket using a port number, Server listens to that port number, when connection is established, server designates a different socket to deal with that Client. Uses TCP as a connection protocol.

In this program, Server listens for connection to be made. Once there is a connection. The client can send basic math calculation eg (2 + 2) and the server responds with the answer 4. 

There can be multiple clients connected to one server. For each client, a separate thread is made. 

Each client can send multiple request, and also tell the server when to stop the connection. 
