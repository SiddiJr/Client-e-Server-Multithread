import java.io.*;
import java.net.Socket;
<<<<<<< HEAD
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static DataOutputStream dataOutputStream = null;
=======

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
<<<<<<< HEAD
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            String line;
            while (true) {
                line = in.readLine();

                if(line.equalsIgnoreCase("sair")){
                    System.out.printf("Fechando conexão com %s ...\n", clientSocket.getInetAddress().getHostAddress());
                    break;
                }
                else if(line.equalsIgnoreCase("arquivo")) {
                    File file = new File("C:\\Users\\sidne\\Desktop\\teste\\servidor\\foi.txt");
                    String hash = sha(file);
                    long length = Files.size(file.toPath());
                    String string = Long.toString(length);
                    out.println("foi.txt");
                    out.println(string);
                    out.println(hash);
                    sendFile("C:\\Users\\sidne\\Desktop\\teste\\servidor\\foi.txt");
                    out.println("ok");
                } else {
                    System.out.println("faz algo");
                }

            }
        }
        catch (Exception e) {
=======

            String line;
            while ((line = in.readLine()) != null) {
                System.out.printf("Sent from the client: %s\n", line);
                out.println(line);
            }
        }
        catch (IOException e) {
>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
<<<<<<< HEAD
                    dataOutputStream.close();
=======
>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
<<<<<<< HEAD
                System.out.println("erro");
            }
        }
    }

    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // send file size
	System.out.println(file.length());
        dataOutputStream.writeLong(file.length());
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }

    public String sha(File file) throws NoSuchAlgorithmException, IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(bytes);
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
=======
                e.printStackTrace();
            }
        }
    }
>>>>>>> eabae7f6be49ac5e9c9d70349b89c2cef66eb6f6
}
