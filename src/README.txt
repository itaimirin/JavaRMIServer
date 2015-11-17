READ ME:
------------------------------------
AUTHOR: ITAI MARONGWE 100885911
------------------------------------

CLIENT.java is the RMI Client that invokes the remote methods from the Server.
-is allowed to invoke the remote methods handled by the server

SERVER.java is  the RMI Server to which the remote methods are invoke from. This server
handles the RMI Servers  remote methods invoked by the client:
	1. list (no arguments): When invoked will display the available seats
	2. passengers (no arguments): When invoked will display the passengers list 
	3. reserve: <Class> <Name> <seat Number>: When invoked will reserve a seat 
		takes 3
		
HELLO.java is the RMI Client/Servers Interface to which recieves remote methods


I have more notes about what I learned within the .java files.