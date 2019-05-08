import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedEchoHandler implements Runnable {

    private final Socket incoming;
    private final InputStream inStream;
    private final OutputStream outStream;
    private final Q q;
    private String threadName;
    private PrintWriter out;

    ThreadedEchoHandler(Socket socket, Q q) throws IOException {
        this.incoming = socket;
        inStream = incoming.getInputStream();
        outStream = incoming.getOutputStream();
        this.q = q;
        new Thread(this).start();
    }

    @Override
    public void run() {

        try(Scanner sc = new Scanner(inStream, "UTF-8")) {

            out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"),
                    true);

            out.println("Enter your name:");
                threadName = sc.nextLine();

            System.out.println("Name: " + threadName);

            boolean done = false;

            while (!done && sc.hasNextLine()){
                
                String line = sc.nextLine();
                q.put(threadName + ":\t\t" + line);

                if (line.trim().equals("BYE")) done = true;
            }
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(String s){
        out.println(s);
    }

    public String getThreadName() {
        return threadName;
    }
}
