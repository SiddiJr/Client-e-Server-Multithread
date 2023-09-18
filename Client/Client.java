package Client;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.security.*;

public class Client {
    private static DataInputStream dataInputStream = null;
    private static String path = "C:/Users/sidne/Desktop/teste/client/";

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 11000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataInputStream = new DataInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);
            String line = null;

            while (true) {
                System.out.print("Arquivo ou sair? ");
                line = sc.nextLine();
                out.println(line);

                if(line.equalsIgnoreCase("sair")) {
                    Thread.sleep(500);
                    System.out.println("Fechando conexão...");
                    break;
                }
                else if(line.contains("arquivo")){
                    line = line.replaceAll("arquivo ", "");
                    String result = in.readLine();

                    if(result.equals("ok")) {
                        String name = in.readLine();
                        String length = in.readLine();
                        String hash = in.readLine();

                        receiveFile(path + line);
                        System.out.printf("O arquivo %s tem tamanho %s bytes, tem a hash %s e o resposta foi %s\n"
                                , name, length, hash, result);

                        if (sha(path + line).equals(hash)) {
                            System.out.println("The hashes are the same ");
                        }
                    } else if(result.equalsIgnoreCase("nok")) {
                        System.out.printf("A resposta foi %s, pois o arquivo requisitado não existe!\n", result);
                    }
                }
                out.flush();
            }
            sc.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(socket != null) {
                socket.close();
            }
            if(dataInputStream != null) {
                dataInputStream.close();
            }
        }
    }

    private static void receiveFile(String fileName) throws Exception
    {
        int bytes = 0;
        //File file = new File("Users/sidne/Documents/BSI/Redes de Computadores/Trabalho 1/Client/a.txt");
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Will create parent directories if not exists
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }

    public static String sha(String fileName) throws NoSuchAlgorithmException, IOException {
        File file = new File(fileName);
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
