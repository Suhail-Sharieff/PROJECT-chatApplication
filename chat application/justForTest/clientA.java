import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

public class clientA {
    private JTextField textField;
    private PrintWriter printWriter;
    private BufferedReader reader;
    private Socket socket;

    public void go() {
        setUpNetworking();

        textField = new JTextField(20);
        JButton button = new JButton("Send");
        button.addActionListener(e -> sendMessage());

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(button);

        JFrame frame = new JFrame("Client Chat By Suhail");
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(400, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void sendMessage() {
        try {
            if (printWriter != null) {
                printWriter.println(textField.getText());
                printWriter.flush();

                // Receive server's message
                String response = reader.readLine();
                if (response != null) {
                    textField.setText(response);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error sending/receiving message: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        textField.setText("");
        textField.requestFocus();
    }

    public void setUpNetworking() {
        try {
            System.out.println("Waiting for server..");
            socket = new Socket("localhost", 5000);
            System.out.println("Networking established with server successfully");

            // Initialize PrintWriter and BufferedReader
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new clientA().go();
    }
}
