package SmartBord.controller;

import SmartBord.model.user.UserModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class ProfileController {

    @FXML
    private Button ok;

    @FXML
    private TextField LastName;

    @FXML
    private TextField FirstName;

    @FXML
    private ImageView imageTest;

    @FXML
    private Label username;

    @FXML
    private Label profileMessage;

    private final Stage stage;
    private final UserModel userModel;
    private String path;

    public ProfileController(UserModel userModel) {
        this.stage = new Stage();
        this.userModel = userModel;
    }

    @FXML
    public void initialize() {
        imageClick();
        okButtonOnAction();
    }

    public void okButtonOnAction() {

        ok.setOnAction(event -> {
            InputStream inputStream;
            if (!LastName.getText().isBlank()
                    && !FirstName.getText().isBlank()) {
                boolean regularUser;
                try {
                    if (getPath() != null) {
                        File file = new File(getPath());
                        inputStream = new FileInputStream(file);
                    } else {
                        inputStream = userModel.getUserDao().getUserImage(userModel.getCurrentUser().getUsername());
                    }

                    //add user to database and set current user
                    regularUser = userModel.getUserDao()
                            .updateUser(inputStream, userModel.getCurrentUser().getUsername(),
                                    userModel.getCurrentUser().getPassword(),
                                    FirstName.getText(),
                                    LastName.getText());
                    userModel.getCurrentUser().setRegisterFirstName(FirstName.getText());
                    userModel.getCurrentUser().setRegisterLastName(LastName.getText());
                    //if user return is not null
                    if (regularUser) {
                        profileMessage.setText("Update " + userModel.getCurrentUser().getUsername());
                        profileMessage.setTextFill(Color.GREEN);
                        stage.close();
                    } else {
                        profileMessage.setText("Cannot Update");
                        profileMessage.setTextFill(Color.RED);
                    }
                } catch (SQLException | FileNotFoundException e) {
                    profileMessage.setText(e.getMessage());
                    System.out.println(e.getMessage());
                    profileMessage.setTextFill(Color.RED);
                }
            }

        });

    }

    public void cancelButtonOnAction() {
        stage.close();
    }

    public void imageShowIt() throws SQLException {
        imageTest.setImage(new Image(userModel.getUserDao().getUserImage(userModel.getCurrentUser().getUsername())));
        username.setText(userModel.getCurrentUser().getUsername());
        FirstName.setText(userModel.getCurrentUser().getRegisterFirstName());
        LastName.setText(userModel.getCurrentUser().getRegisterLastName());
    }

    public void imageClick() {
        imageTest.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(stage);
            if (file==null) return;
            Path(file.getPath());
            InputStream input;
            try {
                input = new FileInputStream(file);
                imageTest.setFitWidth(63);
                imageTest.setImage(new Image(input));
            } catch (IOException e) {
                Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void Path(String path) {
        this.path = path;
    }

    private String getPath() {
        //if user didn't choose image then System to choose!
        return path;
    }

    public void showStage(Parent root) throws SQLException {
        imageShowIt();
        Scene scene = new Scene(root, 400, 201);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}