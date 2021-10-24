package SmartBord.main;


import SmartBord.controller.LoginController;
import SmartBord.model.user.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;

public class StartApplication extends Application {
    private UserModel userModel;

    @Override
    public void init() {userModel = new UserModel();}

    @Override
    public void start(Stage primaryStage) {
        try {
            userModel.setup();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Login.fxml"));
            Callback<Class<?>, Object> controllerFactory = param -> new LoginController(primaryStage, userModel);
            loader.setControllerFactory(controllerFactory);
            Parent root = loader.load();
//            System.out.println(getClass().getResource("../../Login.fxml").getPath());
            LoginController loginController = loader.getController();
            loginController.showStage(root);
        } catch (IOException | SQLException | RuntimeException e) {
            Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
            primaryStage.setTitle("Error");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
