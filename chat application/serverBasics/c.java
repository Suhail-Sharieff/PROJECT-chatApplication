package serverBasics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class c {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("localhost", 5000);
            //send msg to server
            PrintWriter writer=new PrintWriter(socket.getOutputStream(), true);
            writer.println("hi, im ur client");
            //read ms from server
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msgFromServer=reader.readLine();
            System.out.println("Server: "+msgFromServer);
            reader.close();
            socket.close();
        } catch (IOException e) {
            
        }
        
    }
}
