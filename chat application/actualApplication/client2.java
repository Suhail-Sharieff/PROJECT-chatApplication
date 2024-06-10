package actualApplication;


import javax.swing.*;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.nio.charset.StandardCharsets.UTF_8;



public class client2 extends JFrame{
    //Every server is going to have its own reader as well as the writer and we have two text areas that is incoming and outgoing
    private JTextArea incoming;
    private JTextField outgoing;
    private BufferedReader reader;
    private PrintWriter writer;
    //go()
    public void go(){
        //setUpNetwork first
        setUpNetwork();
        //adding scroller to incoming
        JScrollPane scroller=createScrollableTextArea();
        //intitializing outgoing
        outgoing=new JTextField(20);
        //btn and action on click
        JButton send=new JButton("Send");
        send.addActionListener(e->sendMessage());
        //make panel and add outgoing
        JPanel panel=new JPanel();
        panel.add(scroller);
        panel.add(outgoing);
        panel.add(send);
        //IMP
        //assigning executor a job via single thread
        ExecutorService executor=Executors.newSingleThreadExecutor();
        executor.execute(new incomingReader());//coz its a runnable class

        //make frame
        this.getContentPane().add(BorderLayout.CENTER,panel);
        this.setSize(400,350);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void setUpNetwork(){
        try {
            InetSocketAddress serverAddress=new InetSocketAddress("127.0.0.1", 5000);
            SocketChannel socketChannel=SocketChannel.open(serverAddress);
           //initialize writer and reader
           writer=new PrintWriter(Channels.newWriter(socketChannel, UTF_8));
           reader=new BufferedReader(Channels.newReader(socketChannel, UTF_8));
           
           System.out.println("network established with server");
        } catch (Exception e) {
            
        }
    }
    public JScrollPane createScrollableTextArea(){
        //initialize incoming
        incoming=new JTextArea(15,30);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane scroller=new JScrollPane(incoming);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scroller;
    }
    public void sendMessage(){
        writer.println("Client 2: " +outgoing.getText());
        writer.flush();
        outgoing.setText("");
        outgoing.requestFocus();
    }
    public class incomingReader implements Runnable{
        String msg;
        public void run() {
           try {
                while ((msg=reader.readLine())!=null) {
                    System.out.println(msg);
                    incoming.append(msg+"\n");
                }
           } catch (Exception e) {
    
           }
            
        }
        
    }
    public static void main(String[] args) {
        new client2().go();
    }

}

