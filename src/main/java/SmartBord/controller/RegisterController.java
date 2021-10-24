package SmartBord.controller;

import SmartBord.model.user.RegularUser;
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
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField registerUserName;

    @FXML
    private TextField registerPassword;

    @FXML
    private TextField registerFirstName;

    @FXML
    private TextField registerLastName;

    @FXML
    private Button closeRegisterButton;

    @FXML
    private Label registerMessage;

    @FXML
    private ImageView image_choose;
    private final Stage stage;
    private final Stage parentStage;
    private final UserModel userModel;
    private String path;

    public RegisterController(Stage parentStage,UserModel userModel) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.userModel = userModel;
    }

    @FXML
    public void initialize() {
        setImageChoose();
        registerCloseButtonOnAction();
    }

    public void registerButtonOnAction(){
        InputStream inputStream;
        if (!registerUserName.getText().isBlank()
                && !registerPassword.getText().isBlank()
                && !registerFirstName.getText().isBlank()
                && !registerLastName.getText().isBlank()){
            RegularUser regularUser;
            try {
                File file = new File(getPath());
                inputStream = new FileInputStream(file);
                //add user to database and set current user
                regularUser = userModel.getUserDao()
                        .createUser(inputStream, registerUserName.getText(),
                                registerPassword.getText(),
                                registerFirstName.getText(),
                                registerLastName.getText());
                //if user return is not null
                if (regularUser != null) {
                    registerMessage.setText("Created " + regularUser.getUsername());
                    registerMessage.setTextFill(Color.GREEN);
                } else {
                    registerMessage.setText("Cannot create user");
                    registerMessage.setTextFill(Color.RED);
                }
            } catch (SQLException | FileNotFoundException e) {
                registerMessage.setText(e.getMessage());
                registerMessage.setTextFill(Color.RED);
            }
        }else {
            registerMessage.setText("please enter username, password, FirstName and LastName.");
        }
    }

    public void registerCloseButtonOnAction(){
        closeRegisterButton.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }

    public void setImageChoose(){
        image_choose.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(stage);

            // if the user does not choose anything, stop the execution of the method here
            if(file == null) return;
            Path(file.getPath());
            InputStream input;
            try {
                input = new FileInputStream(file);
                image_choose.setFitWidth(63);
                image_choose.setImage(new Image(input));
            } catch (IOException e) {
                Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void Path(String path){
        this.path =path;
    }

    private String getPath() {
        //if user didn't choose image then System to choose!
        if (path == null) {
            this.path = "src/main/resources/R7VzUr.jpg";
        }
        return path;
    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 520, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}