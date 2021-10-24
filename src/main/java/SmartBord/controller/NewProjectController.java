package SmartBord.controller;

import SmartBord.exception.SmartBordException;
import SmartBord.model.project.Project;
import SmartBord.model.user.RegularUser;
import SmartBord.model.user.UserModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NewProjectController {

    @FXML
    private TextField NewProjectName;

    @FXML
    private Label Message;


    private final Stage stage;
    private final UserModel userModel;
    private String name;
    private boolean rename;

    public NewProjectController(UserModel userModel){
        this.stage = new Stage();
        this.userModel = userModel;
    }

    public void cancelOnAction(){
            stage.close();
    }

    public void addNewProject() throws SmartBordException {
        if (!NewProjectName.getText().isBlank()) {
            RegularUser regularUser = userModel.getCurrentUser();

            if (rename){
                Project project =  regularUser.findProject(name);
                project.setProjectName(projectName());
                stage.close();
                return;
            }

            if (regularUser.addProject(NewProjectName.getText())) {
                Message.setText("Created " + NewProjectName);
                Message.setTextFill(Color.GREEN);
                stage.close();
            } else {
                Message.setText("Cannot create user");
                Message.setTextFill(Color.RED);
                NewProjectName.clear();
            }
        }
    }

    public void projectEdit(boolean renamed){
        this.rename = renamed;
    }

    public String projectName(){
        return NewProjectName.getText();
    }

    public void setNewProjectName(String projectName){
        NewProjectName.setText(projectName);
    }
    public void setOldProjectName(String projectName){
        this.name = projectName;

    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
