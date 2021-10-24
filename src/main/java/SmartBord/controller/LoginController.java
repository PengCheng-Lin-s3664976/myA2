package SmartBord.controller;

import SmartBord.model.project.Project;
import SmartBord.model.project.ProjectSystem;
import SmartBord.model.user.RegularUser;
import SmartBord.model.user.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private TextField usernameTestField;

    @FXML
    private Label loginMessage;

    @FXML
    private PasswordField passwordTestField;

    private final Stage stage;
    private final UserModel userModel;

    public LoginController(Stage stage, UserModel userModel) {
        this.stage = stage;
        this.userModel = userModel;
    }


    public void loginCloseButtonOnAction() {
        stage.close();
    }

    public void registerButtonOnAction() {
        //if user click go to this method
        registerPage();
    }

    public void registerPage() {
        try {
            //load fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Register.fxml"));

            Callback<Class<?>, Object> controllerFactory = param -> new RegisterController(stage, userModel);
            loader.setControllerFactory(controllerFactory);

            Parent root = loader.load();
            RegisterController registerController = loader.getController();
            registerController.showStage(root);
            stage.close();
        } catch (Exception e) {
            loginMessage.setText(e.getMessage());
            loginMessage.setTextFill(Color.RED);
        }
    }

    public void loginButtonOnAction() {
        if (!usernameTestField.getText().isBlank() && !passwordTestField.getText().isBlank()) {
            homePage();
        } else {
            loginMessage.setText("please enter username and password.");
            loginMessage.setTextFill(Color.RED);
        }
        usernameTestField.clear();
        passwordTestField.clear();
    }

    public void homePage() {
        RegularUser regularUser;
        ArrayList<Project> list;
        ProjectSystem projectSystem;
        try {
            regularUser = userModel.getUserDao().getUser(usernameTestField.getText(), passwordTestField.getText());
            projectSystem = userModel.getProjectDao().getProject(usernameTestField.getText());

            if (projectSystem!=null){
                list = userModel.getProjectDao().getProject(usernameTestField.getText()).getList();

                regularUser.setProjects(list);

            }

            if (regularUser != null) {
                userModel.setCurrentUser(regularUser);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../HomePage.fxml"));

                Callback<Class<?>, Object> controllerFactory = param -> new HomeController(stage, userModel);
                loader.setControllerFactory(controllerFactory);
                Parent root = loader.load();
                HomeController homeController = loader.getController();
                System.out.println("2");
                homeController.showStage(root);
                System.out.println("1");
                stage.close();
            } else {
                loginMessage.setText("Wrong username or password");
                loginMessage.setTextFill(Color.RED);
            }

        } catch (SQLException | IOException e) {
            loginMessage.setText(e.getMessage());
            loginMessage.setTextFill(Color.RED);
        }
    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 520, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
