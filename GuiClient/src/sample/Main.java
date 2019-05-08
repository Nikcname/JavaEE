package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Socket socket = null;
        final PrintWriter out;
        final BufferedReader in;
        ObservableList<String> items = FXCollections.observableArrayList();


        try{
            socket = new Socket("localhost", 8189);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e){
            return;
        }

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVBox = new VBox(20);

        Text serverAsk = new Text(in.readLine());
        TextField nameInit = new TextField();

        HBox choiseHBox = new HBox(20);
        choiseHBox.setSpacing(20);
        Button setName = new Button("Ok");

        Text name = new Text("");
        setName.setOnAction(
                (ActionEvent event) ->{
                    out.println(nameInit.getText());
                    name.setText(nameInit.getText());
                    dialog.close();
                }
        );

        Button cnclBtn = new Button("Cancel");
        cnclBtn.setOnAction(
                (ActionEvent event)->{
                    dialog.close();
                    primaryStage.close();
                }
        );
        choiseHBox.getChildren().addAll(setName, cnclBtn);

        dialogVBox.getChildren().addAll(serverAsk, nameInit, choiseHBox);

        Scene dialogScene = new Scene(dialogVBox, 200, 200);
        dialog.setScene(dialogScene);


//        out.close();
//        in.close();
//        socket.close();

        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        hBox.setSpacing(20);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(name);

        hBox.getChildren().add(stackPane);


        ListView<String> listView = new ListView<>();

        listView.setItems(items);

        borderPane.setCenter(listView);

        borderPane.setTop(hBox);

        HBox stackPane1 = new HBox();
        stackPane1.setSpacing(20);
        TextField message = new TextField();

        Button buttonSend = new Button("Send");

        buttonSend.setOnAction(
                (ActionEvent event) ->{
                    if (!message.getText().equals("")){
                        out.println(message.getText());
                        message.setText("");
                    }
                }
        );

        stackPane1.getChildren().addAll(message, buttonSend);
        
        borderPane.setBottom(stackPane1);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(borderPane, 300, 275));
        primaryStage.show();
        dialog.show();

        new Thread(()->{
            while (true){
                try {
                    items.add(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
