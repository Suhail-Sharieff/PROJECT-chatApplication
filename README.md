![Screenshot (24)](https://github.com/Suhail-Sharieff/PROJECT-chatApplication/assets/149879419/4be94b1e-774c-4f46-a9c1-7695a9a6793c)

This is basically a simple chat application that I have built after learning socket programming.
The Gui of this chat application need to be improved yet  i would do later
task is to make it cpable of creating unlimited users's objects rather than client1, 2 etc

### Execution Flow

1. **`main` Method**:
   - The `main` method starts the application by calling the `go` method on an instance of the `server` class.
   ```java
   public static void main(String[] args) {
       new server().go();
   }
   ```

2. **`go` Method**:
   - **Initialize `clientOutputStreams`**: An `ArrayList` to store `PrintWriter` objects for each client.
   - **Create `ServerSocket`**: Listens for incoming client connections on port 5000.
   - **Infinite Loop**: Continuously waits for and accepts new client connections.
     ```java
     while (true) {
         Socket clientSocket = serverSock.accept();
         PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
         clientOutputStreams.add(writer);

         Thread t = new Thread(new ClientHandler(clientSocket));
         t.start();
         System.out.println("some client got connected with server");
     }
     ```

   - **Accept Client Connection**: Blocks until a new client connects, then accepts the connection.
   - **Create `PrintWriter`**: For the connected client and adds it to `clientOutputStreams`.
   - **Start Client Handler Thread**: Creates and starts a new `ClientHandler` thread for the client.

3. **`ClientHandler` Class**:
   - Each `ClientHandler` handles communication with a single client. It reads messages from the client and broadcasts them to all clients.

   **Constructor**:
   - Initializes the `BufferedReader` to read messages from the client socket.
     ```java
     public ClientHandler(Socket clientSocket) {
         try {
             sock = clientSocket;
             InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
             reader = new BufferedReader(isReader);
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     }
     ```

   **run Method**:
   - Runs in a separate thread for each client. It reads messages from the client in a loop and broadcasts them to all clients.
     ```java
     public void run() {
         String message;
         try {
             while ((message = reader.readLine()) != null) {
                 System.out.println(message);
                 tellEveryone(message);
             }
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     }
     ```

   - **Read Messages**: Continuously reads messages from the client.
   - **Broadcast Messages**: Calls `tellEveryone` to broadcast the received message to all clients.

4. **`tellEveryone` Method**:
   - Iterates over all `PrintWriter` objects in `clientOutputStreams` and sends the message to each client.
   ```java
   public void tellEveryone(String message) {
       Iterator<PrintWriter> it = clientOutputStreams.iterator();
       while (it.hasNext()) {
           try {
               PrintWriter writer = (PrintWriter) it.next();
               writer.println(message);
               writer.flush();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
       }
   }
   ```

   - **Iterate and Send Messages**: For each client, sends the message and flushes the stream to ensure it's sent immediately.

### Summary

- **main**: Calls `go`.
- **go**: Sets up the server, waits for clients to connect, and starts a new `ClientHandler` thread for each client.
- **ClientHandler**: Reads messages from the client and calls `tellEveryone` to broadcast the message.
- **tellEveryone**: Sends the message to all connected clients using their respective `PrintWriter` objects.

Each client's communication is handled in a separate thread, allowing multiple clients to interact with the server concurrently.

