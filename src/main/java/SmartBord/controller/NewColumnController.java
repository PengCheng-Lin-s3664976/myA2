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


public class NewColumnController {

    @FXML
    private TextField NewColumnName;

    @FXML
    private Label Message;

    private final Stage stage;
    private boolean check;
    private final UserModel userModel;
    private final String nameOfTab;

    public NewColumnController(UserModel userModel, String nameOfTab) {
        this.stage = new Stage();
        this.userModel = userModel;
        this.nameOfTab = nameOfTab;
    }

    public void addNewColumn() throws SmartBordException {
        if (!NewColumnName.getText().isBlank()) {
            RegularUser regularUser = userModel.getCurrentUser();

            for ( Project project :regularUser.getProjects()) {
                if (nameOfTab.equals(project.getProjectName())){
                   check = project.addColumn(NewColumnName.getText());
                   if (check){
                       Message.setText("Created " + NewColumnName.getText());
                       Message.setTextFill(Color.GREEN);
                       stage.close();
                       return;
                   }
                    check = false;
                    Message.setText("have same column");
                    Message.setTextFill(Color.RED);
                }
            }
        }

    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
