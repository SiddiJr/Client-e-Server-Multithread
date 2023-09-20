package Client;

import java.util.Scanner;
import java.io.*;

public class ClientChatOutput  implements Runnable {
        private static PrintWriter out;
        private boolean flag;

        public ClientChatOutput(PrintWriter out) {
            this.out = out;
            this.flag = true;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);
            String line;

            while(flag) {
                line = sc.nextLine();
                out.println(line);

                if(line.equalsIgnoreCase("sair")) {
                    flag = false;
                }
            }
        }
    }
