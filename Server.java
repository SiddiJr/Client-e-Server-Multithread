import java.io.*;
import java.net.*;

class Server {
<<<<<<< HEAD
    public static Socket client;
=======
>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(11000);
            server.setReuseAddress(true);

            while(true) {
<<<<<<< HEAD
                client = server.accept();

                System.out.println("New client accepted: " + client.getInetAddress().getHostAddress());
=======
                Socket client = server.accept();

                System.out.println("New client accepted" + client.getInetAddress().getHostAddress());
>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
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