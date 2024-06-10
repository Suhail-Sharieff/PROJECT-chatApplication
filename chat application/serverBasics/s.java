package serverBasics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class s {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(5000);
            System.out.println("Waiting for clients to connect...");
            Socket pool = socket.accept();
            System.out.println("Got a connection");
            // send msg to cleint
            PrintWriter writer = new PrintWriter(pool.getOutputStream(), true);
            writer.println("hi im ur server");
            // read client's msg
            BufferedReader reader = new BufferedReader(new InputStreamReader(pool.getInputStream()));
            String msgFromClient = reader.readLine();
            System.out.println("Client: " + msgFromClient);
            reader.close();

            socket.close();

        } catch (Exception e) {

        }

    }
}
