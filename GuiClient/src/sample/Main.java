package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static BridgeSocket bridgeSocket;

    @Override
    public void start(Stage primaryStage) throws Exception{

        bridgeSocket = new BridgeSocket("localhost", 8189);

        if (bridgeSocket.connect().equals("fail"))
            return;

        PopUpDialog popUpDialog = new PopUpDialog(primaryStage, bridgeSocket);

        String clientName = popUpDialog.getName();

        MainDialog mainDialog = new MainDialog(clientName, bridgeSocket);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(mainDialog.getBorderPane(), 260, 275));
        primaryStage.show();
        popUpDialog.show();

        new SocketListener(bridgeSocket, mainDialog.getItems());
    }


    public static void main(String[] args) throws IOException {
        launch(args);
        bridgeSocket.getSocket().close();
    }
}
