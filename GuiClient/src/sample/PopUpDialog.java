package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpDialog extends Stage {

    private Text enterName;

    public String getName() {
        return enterName.getText();
    }

    public PopUpDialog(Stage primaryStage, BridgeSocket bridgeSocket) throws IOException {

        this.initModality(Modality.APPLICATION_MODAL);
        this.initOwner(primaryStage);

        Text serverAsk = new Text(bridgeSocket.getIn().readLine());
        TextField nameInit = new TextField();

        Button setName = new Button("Ok");
        enterName = new Text("");

        setName.setOnAction(
                e ->{
                    bridgeSocket.getOut().println(nameInit.getText());
                    enterName.setText(nameInit.getText());
                    this.close();
                });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(
                e ->{
                    this.close();
                    primaryStage.close();
                });

        VBox dialogVBox = new VBox(20);
        HBox chooseHBox = new HBox(20);

        chooseHBox.getChildren().addAll(setName, cancelButton);
        chooseHBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().addAll(serverAsk, nameInit, chooseHBox);
        dialogVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(dialogVBox, 200, 140);
        this.setScene(dialogScene);
    }
}
