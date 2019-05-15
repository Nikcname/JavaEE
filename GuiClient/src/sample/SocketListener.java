package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.IOException;

public class SocketListener implements Runnable {

    private BridgeSocket bridgeSocket;
    private ObservableList<String> items;
    private boolean running;

    public SocketListener(BridgeSocket bridgeSocket, ObservableList<String> items) {
        this.bridgeSocket = bridgeSocket;
        this.items = items;
        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running){

            String recText = null;

            try {
                recText = bridgeSocket.getIn().readLine();
            } catch (IOException e) {
                running = false;
            }

            String finalRecText = recText;
            Platform.runLater(()->items.add(finalRecText));
        }

    }
}
