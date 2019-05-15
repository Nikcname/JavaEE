package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainDialog {

    private ObservableList<String> items;
    private BorderPane borderPane;

    public ObservableList<String> getItems() {
        return items;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public MainDialog(String clientName, BridgeSocket bridgeSocket) {

        borderPane = new BorderPane();

        HBox headerBox = new HBox(20);
        HBox bottomBox = new HBox(20);
        HBox centerBox = new HBox(20);
        HBox leftBox = new HBox(20);
        HBox rightBox = new HBox(20);

        headerBox.setPadding(new Insets(5, 5, 0, 5));
        centerBox.setPadding(new Insets(5, 5, 5, 5));
        bottomBox.setPadding(new Insets(0, 5, 5, 5));

        Text stageTag = new Text(clientName);

        ListView<String> listView = new ListView<>();

        items = FXCollections.observableArrayList();
        listView.setItems(items);

        TextField message = new TextField();

        Button buttonSend = new Button("Send");

        buttonSend.setOnAction(
                e ->{
                    if (!message.getText().equals("")){
                        bridgeSocket.getOut().println(message.getText());
                        message.setText("");
                    }
                }
        );

        headerBox.getChildren().add(stageTag);
        centerBox.getChildren().add(listView);
        bottomBox.getChildren().addAll(message, buttonSend);

        borderPane.setTop(headerBox);
        borderPane.setCenter(centerBox);
        borderPane.setBottom(bottomBox);
        borderPane.setLeft(leftBox);
        borderPane.setRight(rightBox);

    }
}
