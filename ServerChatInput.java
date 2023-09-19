import java.io.BufferedReader;
import java.io.IOException;

public class ServerChatInput implements Runnable {

    private static BufferedReader in;
    private boolean flag;

    public ServerChatInput(BufferedReader in) {
        this.in = in;
        this.flag = true;
    }

    @Override
    public void run() {
        String line;

        while(flag) {
            try {
                line = in.readLine();

                System.out.printf("Client: %s\n", line);

                if (line.equalsIgnoreCase("sair")) {
                    System.out.println();
                    flag = false;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}