import java.io.*;
import java.net.*;

public class RPCServer {
    public static void main(String[] args) {
        int port = 7799;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            // Create server socket
            serverSocket = new ServerSocket(port);
            System.out.println("[+] Server socket created.");

            // Wait for client connection
            System.out.println("[+] Listening on port " + port + "...");
            clientSocket = serverSocket.accept();
            System.out.println("[+] Client connected.");

            // Setup input and output streams
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            // Receive two integers from the client
            int a = in.readInt();
            int b = in.readInt();

            // Perform addition
            int result = add(a, b);

            // Send result back to client
            out.writeInt(result);
            System.out.println("[+] Data sent: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
                System.out.println("[+] Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
