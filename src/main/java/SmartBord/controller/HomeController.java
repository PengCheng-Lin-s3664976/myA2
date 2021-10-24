package SmartBord.controller;

import SmartBord.model.project.Column;
import SmartBord.model.project.Project;
import SmartBord.model.project.ProjectSystem;
import SmartBord.model.project.Task;
import SmartBord.model.user.RegularUser;
import SmartBord.model.user.UserModel;
import SmartBord.view.CustomControlColumn;
import SmartBord.view.CustomControlNewTab;
import SmartBord.view.CustomControlTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label profileMessage;

    @FXML
    private ImageView imageInterface;

    @FXML
    private Button profileName;

    @FXML
    private Label homeName;

    @FXML
    private TabPane tabPane;

    @FXML
    private MenuItem homeDelete;

    @FXML
    private MenuItem homeRename;

    @FXML
    private MenuItem setAsDefault;

    @FXML
    private MenuItem unsetDefault;


    private final Stage stage;
    private final Stage parentStage;
    private final UserModel userModel;
    private int tabIndex;
    private int defaultTab;
    private int defaultTabCheck;
    private int saveDefaultTabCheck;
    private int saveDefaultTab;


    public HomeController(Stage parentStage, UserModel userModel) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.userModel = userModel;
        this.defaultTab = 0;
        this.defaultTabCheck = 0;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowClose();
        setHomeDefault();
        profileButtonOnAction();
        homeProjectCloseButtonOnAction();
        homeProjectRenameButtonOnAction();
        homeProjectSetAsDefaultButtonOnAction();
        homeProjectUnsetDefaultButtonOnAction();
        profileMessage.setText("Genius only means hard-working all one’s life.");
        try {
            UpdateView();
        } catch (IOException e) {
            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        }
    }

    private void windowClose() {
        stage.setOnCloseRequest(windowEvent -> windowCloseEvent());
    }

    public void homeLogoutButtonOnAction() {
        windowCloseEvent();
    }

    private void windowCloseEvent() {
        String userName = userModel.getCurrentUser().getUsername();
        ProjectSystem projectSystem = new ProjectSystem();
        projectSystem.setList(userModel.getCurrentUser().getProjects());
        try {
            FileOutputStream fos = new FileOutputStream("src/main/resources/Projects.txt");
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(projectSystem);

            os.flush();
            os.close();
            if (userModel.getProjectDao().getProject(userName) == null) {
                userModel.getProjectDao().createProject(userName, defaultTabCheck, defaultTab);
            } else {
                userModel.getProjectDao().updateProject(userName, saveDefaultTabCheck, saveDefaultTab);
            }
        } catch (IOException | SQLException e) {
            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        }
        stage.close();
        parentStage.show();
    }

    private void UpdateView() throws IOException {

        //remove old tabs
        tabPane.getTabs().clear();

        //set new tabs
        ArrayList<Project> projects = userModel.getCurrentUser().getProjects();
        for (Project project : projects) {

            //set new tab
            CustomControlNewTab customControlNewTab = new CustomControlNewTab();
            customControlNewTab.setText(project.getProjectName());

            //loop column
            for (Column column : project.getColumns()) {

                //set new column
                CustomControlColumn customControlColumn = new CustomControlColumn();
                customControlColumn.getName().setText(column.getColumnName());
                customControlColumn.getColumnGrid().setPrefHeight(15);
                customControlNewTab.getHbox().setPrefWidth(project.getColumns().size() * 211);
                customControlNewTab.getHbox().setSpacing(10);

                customControlColumn.getColumnGrid().setOnDragDropped(dragEvent -> {
                    defaultTabCheck = 0;
                    String[] taskMessage = dragEvent.getDragboard().getString().split(":");
                    try {
                        if (column.notFindTask(taskMessage[1])) {
                            for (Column findColumn : project.getColumns()) {
                                if (findColumn.getColumnName().equals(taskMessage[0])) {
                                    findColumn.deleteTask(taskMessage[1]);
                                }
                            }
                            column.addTask(taskMessage[1], taskMessage[2]);
                        }
                        UpdateView();
                    } catch (Exception e) {
                        Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                        stage.setTitle("Error");
                        stage.setScene(scene);
                        stage.show();
                    }

                });

                //set add task action
                customControlColumn.getAdd().setOnAction(event -> {
                    defaultTabCheck = 0;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../NewTaskPage.fxml"));
                        String nameOfTab = tabPane.getSelectionModel().getSelectedItem().getText();
                        Callback<Class<?>, Object> controllerFactory = param -> new NewTaskController(userModel, nameOfTab, column.getColumnName());
                        loader.setControllerFactory(controllerFactory);
                        Parent root = loader.load();
                        NewTaskController newTaskController = loader.getController();
                        newTaskController.showStage(root);

                        //set default tab
                        tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                        UpdateView();
                    } catch (Exception e) {
                        Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                        stage.setTitle("Error");
                        stage.setScene(scene);
                        stage.show();
                    }
                });

                customControlColumn.getDeleteColumn().setOnAction(event -> {
                    try {
                        defaultTabCheck = 0;
                        project.deleteColumn(column.getColumnName());
                        //set default tab
                        tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                        UpdateView();
                    } catch (Exception e) {
                        Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                        stage.setTitle("Error");
                        stage.setScene(scene);
                        stage.show();
                    }
                });
                //loop task
                for (Task task : column.getTasks()) {

                    //set Task
                    CustomControlTask customControlTask = new CustomControlTask(task.getDescription());
                    customControlTask.getTaskName().setText(task.getTaskName());
                    customControlColumn.getVbox().setPrefHeight(column.getTasks().size() * 210);
                    customControlColumn.getVbox().setSpacing(10);

                    customControlTask.getAnchor().setOnDragDetected(mouseEvent -> {
                        defaultTabCheck = 0;
                        Dragboard db = customControlTask.getAnchor().startDragAndDrop(TransferMode.COPY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(column.getColumnName() + ":" + task.getTaskName() + ":" + task.getDescription() + ":" + column.getTasks().indexOf(task));
                        db.setContent(content);
//                        column.deleteTask(task.getTaskName());
                        try {
                            tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                            UpdateView();
                            mouseEvent.consume();
                        } catch (IOException e) {
                            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                            stage.setTitle("Error");
                            stage.setScene(scene);
                            stage.show();
                        }
                    });

                    customControlTask.getAnchor().setOnDragDropped(dragEvent -> {
                        defaultTabCheck = 0;
                        String[] taskMessage = dragEvent.getDragboard().getString().split(":");
                        try {
                            int i = column.getTasks().indexOf(task);
                            if (!taskMessage[0].equals(column.getColumnName())) {
                                for (Column findColumn : project.getColumns()) {
                                    if (taskMessage[0].equals(findColumn.getColumnName())) {
                                        if (findColumn.notFindTask(taskMessage[1])) {
                                            findColumn.deleteTask(taskMessage[1]);
                                            column.DragTask(taskMessage[1], taskMessage[2], i);
                                        }
                                    }
                                }
                            }else {
                                column.deleteTask(taskMessage[1]);
                                column.DragTask(taskMessage[1], taskMessage[2], i);
                            }

                            UpdateView();
                            dragEvent.consume();
                        } catch (Exception e) {
                            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                            stage.setTitle("Error");
                            stage.setScene(scene);
                            stage.show();
                        }

                    });

                    //set Delete on Action
                    customControlTask.getDelete().setOnAction(event -> {
                        try {
                            column.deleteTask(task.getTaskName());
                            tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                            UpdateView();
                        } catch (Exception e) {
                            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                            stage.setTitle("Error");
                            stage.setScene(scene);
                            stage.show();
                        }
                    });

                    customControlTask.getEdit().setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../NewTaskPage.fxml"));
                            String nameOfTab = tabPane.getSelectionModel().getSelectedItem().getText();
                            Callback<Class<?>, Object> controllerFactory = param -> new NewTaskController(userModel, nameOfTab, column.getColumnName());
                            loader.setControllerFactory(controllerFactory);
                            Parent root = loader.load();
                            NewTaskController newTaskController1 = loader.getController();
                            newTaskController1.taskEdit(true);
                            newTaskController1.setTaskName(task.getTaskName());
                            newTaskController1.setDescription(task.getDescription());
                            newTaskController1.showStage(root);
                            tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                            UpdateView();
                        } catch (Exception e) {
                            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                            stage.setTitle("Error");
                            stage.setScene(scene);
                            stage.show();
                        }
                    });
                    //add task pane to column pane
                    customControlColumn.getVbox().getChildren().add(customControlTask);
                }
                //add column pane to tab
                customControlNewTab.getHbox().getChildren().add(customControlColumn);
            }
            //add tab to tab pane
            tabPane.getTabs().add(customControlNewTab);
        }
        if (tabPane.getTabs().size() != 0) {
//            BOOLEAN defaultTabCheck
            if (defaultTabCheck == 1 &&saveDefaultTabCheck ==1) {
                tabPane.getSelectionModel().select(defaultTab);
            } else {
                tabPane.getSelectionModel().select(tabIndex);
            }
        }
    }

    public void homeProjectButtonOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../NewProjectPage.fxml"));

            Callback<Class<?>, Object> controllerFactory = param -> new NewProjectController(userModel);
            loader.setControllerFactory(controllerFactory);

            Parent root = loader.load();
            NewProjectController newProjectController = loader.getController();
            newProjectController.showStage(root);
            tabIndex = tabPane.getSelectionModel().getSelectedIndex();
            defaultTabCheck = 0;
            UpdateView();
        } catch (Exception e) {
            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        }
    }

    private void homeProjectCloseButtonOnAction() {
        RegularUser regularUser = userModel.getCurrentUser();
        homeDelete.setOnAction(event -> {
            if (regularUser.getProjects().size() == 0) {
                return;
            }
            boolean checkRemove = regularUser.deleteProject(tabPane.getSelectionModel().getSelectedItem().getText());
            if (checkRemove) {
                tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
                try {
                    defaultTabCheck = 0;
                    UpdateView();
                } catch (IOException e) {
                    Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                    stage.setTitle("Error");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });
    }

    private void homeProjectRenameButtonOnAction() {
        RegularUser regularUser = userModel.getCurrentUser();
        homeRename.setOnAction(event -> {
            defaultTabCheck = 0;
            if (regularUser.getProjects().size() == 0) {
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../NewProjectPage.fxml"));

                Callback<Class<?>, Object> controllerFactory = param -> new NewProjectController(userModel);
                loader.setControllerFactory(controllerFactory);

                Parent root = loader.load();
                NewProjectController newProjectController = loader.getController();
                newProjectController.projectEdit(true);
                newProjectController.setOldProjectName(tabPane.getSelectionModel().getSelectedItem().getText());
                newProjectController.setNewProjectName(tabPane.getSelectionModel().getSelectedItem().getText());
                newProjectController.showStage(root);
                tabIndex = tabPane.getSelectionModel().getSelectedIndex();
                UpdateView();
            } catch (Exception e) {
                Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void homeProjectSetAsDefaultButtonOnAction() {
        setAsDefault.setOnAction(event -> {
            defaultTab = tabPane.getSelectionModel().getSelectedIndex();
            defaultTabCheck = 1;
            saveDefaultTab = defaultTab;
            saveDefaultTabCheck = defaultTabCheck;
        });
    }

    private void homeProjectUnsetDefaultButtonOnAction() {
        unsetDefault.setOnAction(event -> {
            defaultTab = 0;
            defaultTabCheck = 0;
            saveDefaultTab = defaultTab;
            saveDefaultTabCheck = defaultTabCheck;
        });
    }

    private void setHomeDefault() {
        String username = userModel.getCurrentUser().getUsername();
        try {
            if (userModel.getProjectDao().getDefaultTab(username) != null) {
                defaultTab = Integer.parseInt(userModel.getProjectDao().getDefaultTab(username));
                defaultTabCheck = Integer.parseInt(userModel.getProjectDao().getDefaultTabCheck(username));
                saveDefaultTab = defaultTab;
                saveDefaultTabCheck = defaultTabCheck;
            }
        } catch (SQLException e) {
            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void newColumnButtonOnAction() {
        defaultTabCheck = 0;
        ArrayList<Project> projects = userModel.getCurrentUser().getProjects();
        if (projects.size() == 0) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../NewColumnPage.fxml"));
            String nameOfTab = tabPane.getSelectionModel().getSelectedItem().getText();
            Callback<Class<?>, Object> controllerFactory = param -> new NewColumnController(userModel, nameOfTab);
            loader.setControllerFactory(controllerFactory);
            Parent root = loader.load();
            NewColumnController newColumnController = loader.getController();
            newColumnController.showStage(root);
            tabIndex = tabPane.getSelectionModel().getSelectedIndex();
            UpdateView();
        } catch (Exception e) {
            Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        }
    }

    private void profileButtonOnAction() {

        profileName.setOnAction(event -> {
            defaultTabCheck = 0;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ProfilePage.fxml"));

                Callback<Class<?>, Object> controllerFactory = param -> new ProfileController(userModel);
                loader.setControllerFactory(controllerFactory);

                Parent root = loader.load();
                ProfileController profileController = loader.getController();
                profileController.showStage(root);
                this.homeName.setText(userModel.getCurrentUser().getRegisterFirstName() + " " + userModel.getCurrentUser().getRegisterLastName());
                this.imageInterface.setImage(new Image(userModel.getUserDao().getUserImage(userModel.getCurrentUser().getUsername())));
            } catch (Exception e) {
                Scene scene = new Scene(new Label(e.getMessage()+e.getCause()), 200, 100);
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void setImageAndProfileName() throws SQLException {
        this.imageInterface.setImage(new Image(userModel.getUserDao().getUserImage(userModel.getCurrentUser().getUsername())));
        this.homeName.setText(userModel.getCurrentUser().getRegisterFirstName() + " " + userModel.getCurrentUser().getRegisterLastName());
    }

    public void showStage(Parent root) throws SQLException {
        tabPane.setPrefWidth(300);
        tabPane.setPrefHeight(300);
        setImageAndProfileName();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
