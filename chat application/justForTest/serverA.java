import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serverA {
    public static void main(String[] args) {
        try {
            System.out.println("Establishing server...");
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is up and running.");

            while (true) {
                System.out.println("Waiting for a client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Read client's message
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msgFromClient = reader.readLine();
                System.out.println("Message from client: " + msgFromClient);

                // Inform client that server received their message
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Server received your message.");

                // Close resources
                writer.close();
                reader.close();
                clientSocket.close();serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
