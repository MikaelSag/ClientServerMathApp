# Default values for IP and PORT
IP = 127.0.0.1
PORT = 6789

# Compile and run TCP Client
run-client:
	javac TCPClient.java
	java TCPClient $(IP) $(PORT)

# Compile and run TCP Server
run-server:
	javac TCPServer.java
	java TCPServer $(PORT)

# Clean up compiled files
clean:
	rm -rf *.class