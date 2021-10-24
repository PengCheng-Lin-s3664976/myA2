package SmartBord.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomControlTask extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label taskName;

    @FXML
    private Label test;

    @FXML
    private Label test1;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    private String description;
    public CustomControlTask(String description) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CustomControlTask.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.description =description;
        anchor.setPrefWidth(171.0);
        anchor.setPrefHeight(75);
        anchor.setStyle("-fx-background-color: #C6C8FE; -fx-background-radius: 10;");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchor.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource()!=anchor&&dragEvent.getDragboard().hasString()){
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        });
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnchorPane getAnchor() {
        return anchor;
    }

    public void setAnchor(AnchorPane anchor) {
        this.anchor = anchor;
    }

    public Label getTaskName() {
        return taskName;
    }

    public void setTaskName(Label taskName) {
        this.taskName = taskName;
    }

    public Label getTest() {
        return test;
    }

    public void setTest(Label test) {
        this.test = test;
    }

    public Label getTest1() {
        return test1;
    }

    public void setTest1(Label test1) {
        this.test1 = test1;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

}
