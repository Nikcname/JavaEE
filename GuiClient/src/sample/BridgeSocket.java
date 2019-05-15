package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BridgeSocket {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;

    public BridgeSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public String connect(){
        try{
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return "success";

        } catch (IOException e){
            return "fail";
        }
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
}
