import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static DataOutputStream dataOutputStream = null;
    private static String path = "C:/Users/sidne/Desktop/teste/servidor/";

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            String line;
            while (true) {
                line = in.readLine();

                if(line != null && line.equalsIgnoreCase("sair")){
                    System.out.printf("Fechando conexão com %s ...\n", clientSocket.getInetAddress().getHostAddress());
                    break;
                }
                else if(line != null && line.contains("arquivo")) {
                    String arquivo = line.replace("arquivo ", "");
                    File file = new File(path + arquivo);
                    if(file.exists()){
                        out.println("ok");
                        String hash = sha(file);
                        long length = Files.size(file.toPath());
                        String string = Long.toString(length);
                        out.println(arquivo);
                        out.println(string);
                        out.println(hash);
                        sendFile(path + arquivo);
                        Thread.sleep(500);
                    } else {
                        out.println("nok");
                    }
                } else if(line.equalsIgnoreCase("chat")) {
                    ServerChatOutput serverOutput = new ServerChatOutput(out);
                    ServerChatInput serverInput = new ServerChatInput(in);
                    Thread threadOutput = new Thread(serverOutput);
                    Thread threadInput = new Thread(serverInput);
                    threadOutput.start();
                    threadInput.start();
                    threadOutput.join();
                    threadInput.join();
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    dataOutputStream.close();
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                System.out.println("erro");
            }
        }
    }

    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeLong(file.length());
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
}
