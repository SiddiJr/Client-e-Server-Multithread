import java.io.*;
import java.net.*;

class Server {
    public static Socket client;
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(11000);
            server.setReuseAddress(true);

            while(true) {
                Socket client = server.accept();

                System.out.println("New client accepted" + client.getInetAddress().getHostAddress());
                ClientHandler clientSocket = new ClientHandler(client);

                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("error");
        } finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    System.out.println("error");
                }
            }
        }
    }
}