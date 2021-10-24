package SmartBord.controller;

import SmartBord.exception.SmartBordException;
import SmartBord.model.project.Column;
import SmartBord.model.project.Project;
import SmartBord.model.user.RegularUser;
import SmartBord.model.user.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NewTaskController {

    @FXML
    private TextField taskName;

    @FXML
    private TextArea description;

    @FXML
    private Label taskMessage;

    private final Stage stage;
    private boolean taskCheck;
    private boolean taskEdit ;
    private final UserModel userModel;
    private final String nameOfTab;
    private final String columnName;
    private String task;
    public NewTaskController(UserModel userModel, String nameOfTab,String columnName){
        this.stage = new Stage();
        this.userModel = userModel;
        this.nameOfTab = nameOfTab;
        this.columnName = columnName;
    }

    public void taskCancelOnAction(){
        stage.close();
    }

    public void addNewTask() throws SmartBordException {
        if (!taskName.getText().isBlank() && !description.getText().isBlank()) {
            RegularUser regularUser = userModel.getCurrentUser();

            for (Project project :regularUser.getProjects()) {
                if (nameOfTab.equals(project.getProjectName())){
                    for (Column column : project.getColumns()) {
                        if (columnName.equals(column.getColumnName())){
                            if (taskEdit){
                                column.updateTask(task,taskName.getText(),description.getText());
                                stage.close();
                                return;
                            }
                            taskCheck = column.addTask(taskName.getText(),description.getText());
                            if (taskCheck) {
                                taskCheck=true;
                                taskMessage.setText("Created " + taskName);
                                taskMessage.setTextFill(Color.GREEN);
                                stage.close();
                                return;
                            } else {
                                taskCheck=false;
                                taskMessage.setText("Cannot create task");
                                taskMessage.setTextFill(Color.RED);
                                taskName.clear();
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean taskCheck(){
        return taskCheck;
    }

    public void taskEdit(boolean taskEdit){
        this.taskEdit= taskEdit;
    }

    public String taskName(){
        return taskName.getText();
    }

    public void setTaskName(String name){
        this.task = name;
        this.taskName.setText(name);
    }

    public void setDescription(String description){
         this.description.setText(description);
    }

    public String description(){
        return description.getText();
    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 534, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
