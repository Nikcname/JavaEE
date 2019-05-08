import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class EchoServer {

    static ArrayList<ThreadedEchoHandler> arrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        Q q = new Q();

        int i = 1;
        try(ServerSocket ss = new ServerSocket(8189)) {

            Thread messageHandler = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true){
                        String message = q.get();

                        String[] pt = message.split(":\t\t");
                        System.out.println(Arrays.toString(pt));

                        for (ThreadedEchoHandler e : arrayList){
                            e.update(message);
                        }
                    }
                }

            });
            messageHandler.start();

            while (true){

                Socket incoming = ss.accept();
                System.out.println("Spawning " + i);
                i++;
                arrayList.add(new ThreadedEchoHandler(incoming, q));

            }
        }
    }
}
