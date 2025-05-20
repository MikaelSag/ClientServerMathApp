import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        // Check if the right number of parameters are given
        if (argv.length != 1) {
            System.out.println("Use the format: make run-server PORT=<port>");
            return;
        }

        int port = Integer.parseInt(argv[0]);       // port number

        // Create the socket using the port number from the parameter
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println("Server is UP and running on port " + port + "!");

        // Infinite loop to accept multiple clients
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            // Create new thread
            ClientHandler handler = new ClientHandler(connectionSocket);
            handler.start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private String clientName = "Unknown";
    private String clientAddress;
    private long joinTime;

    // Get the address of client
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientAddress = socket.getRemoteSocketAddress().toString();
    }

    public void run() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());

            String inputLine;
            // Continuously read messages from the client
            while ((inputLine = inFromClient.readLine()) != null) {
                System.out.println("Received: " + inputLine);

                String[] parts = inputLine.split("\\|");

                if (parts.length == 0) continue;

                String command = parts[0];

                // Handle client JOIN request
                if (command.equals("JOIN") && parts.length == 2) {
                    clientName = parts[1];
                    joinTime = System.currentTimeMillis();
                    log("JOIN|" + clientName + "|address=" + clientAddress);
                    outToClient.writeBytes("ACK|Welcome " + clientName + "\n");
                }

                // Handle client math calculation request
                else if (command.equals("REQ") && parts.length == 3) {
                    String expression = parts[2];
                    double result = evaluateExpression(expression);
                    long processingTime = 1;
                    String response = "RES|" + clientName + "|" + expression + "|" + result;
                    outToClient.writeBytes(response + "\n");
                    log(response + "|processingTime=" + processingTime + "ms");
                }

                // Handle client QUIT request
                else if (command.equals("QUIT") && parts.length == 2) {
                    log("QUIT|" + clientName);
                    break;
                }

                // Ignore invalid requests
            }

            // Close socket after communication ends
            socket.close();
        } catch (IOException e) {
            System.out.println("Connection error with client " + clientName);
        }
    }

    // Log server activity
    private void log(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("[" + timeStamp + "] " + message);
    }

    // Evaluate math requests (-,+,*,/)
    private double evaluateExpression(String expr) {
        try {
            String[] tokens = expr.split("(?<=[-+*/])|(?=[-+*/])");
            Stack<Double> numbers = new Stack<>();
            Stack<Character> ops = new Stack<>();

            for (String token : tokens) {
                token = token.trim();
                if (token.isEmpty()) continue;

                if ("+-*/".contains(token)) {
                    ops.push(token.charAt(0));
                } else {
                    numbers.push(Double.parseDouble(token));
                }

                while (numbers.size() >= 2 && ops.size() >= 1) {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    char op = ops.pop();
                    double r = switch (op) {
                        case '+' -> a + b;
                        case '-' -> a - b;
                        case '*' -> a * b;
                        case '/' -> a / b;
                        default -> 0;
                    };
                    numbers.push(r);
                }
            }
            return numbers.pop();
        } catch (Exception e) {
            return -1;
        }
    }
}