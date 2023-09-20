package Client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientChatInput implements Runnable {

        private final BufferedReader in;
        private boolean flag;

        public ClientChatInput(BufferedReader in) {
            this.in = in;
            this.flag = true;
        }

        @Override
        public void run() {
            String line;

            while(flag) {
                try {
                    line = in.readLine();

                    System.out.printf("Server: %s\n", line);

                    if (line.equalsIgnoreCase("sair")) {
                        flag = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
