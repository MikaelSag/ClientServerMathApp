import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;

class TCPClient {

    public static void main(String argv[]) throws Exception {
        // Check if the right number of parameters are given
        if (argv.length != 2) {
            System.out.println("Use the format: make run-client IP=<Server_IP> PORT=<Port>");
            return;
        }

        String serverIP = argv[0];      // ip address of the server
        int port = Integer.parseInt(argv[1]);       // port number

        System.out.println("Client is running...");

        // Create the socket using the IP address and port number from the parameters
        Socket clientSocket = new Socket(serverIP, port);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Send JOIN request
        System.out.print("Enter your name: ");
        String clientName = scanner.nextLine();
        String joinMessage = "JOIN|" + clientName + "\n";
        outToServer.writeBytes(joinMessage);

        // Wait for ACK from server
        String serverResponse = inFromServer.readLine();
        if (serverResponse != null && serverResponse.startsWith("ACK|")) {
            System.out.println("FROM SERVER: " + serverResponse);
        } else {
            System.out.println("Did not receive ACK. Closing connection.");
            clientSocket.close();
            return;
        }

        // Send 3 math calculation requests
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter math expression (" + (i + 1) + " of 3): ");
            String expression = scanner.nextLine();
            String requestMessage = "REQ|" + clientName + "|" + expression + "\n";
            outToServer.writeBytes(requestMessage);

            // Receive and display result from server
            String response = inFromServer.readLine();
            System.out.println("FROM SERVER: " + response);

            // Random delay between 1 and 3 seconds
            Thread.sleep(1000 + random.nextInt(2000));
        }

        // Send QUIT request and close connection
        String quitMessage = "QUIT|" + clientName + "\n";
        outToServer.writeBytes(quitMessage);

        System.out.println("Disconnected from server.");
        clientSocket.close();
    }
}