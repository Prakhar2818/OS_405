import java.io.*;
import java.net.*;

public class RPCClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 7799;
        Socket clientSocket = null;
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            // Create client socket
            clientSocket = new Socket(serverAddress, port);
            System.out.println("[+] Client socket created.");

            // Setup input and output streams
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            // Send data
            int a = 10; // First number
            int b = 20; // Second number
            out.writeInt(a);
            out.writeInt(b);
            System.out.println("[+] Data sent.");

            // Receive result
            int result = in.readInt();
            System.out.println("[+] Result received: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                System.out.println("[+] Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
