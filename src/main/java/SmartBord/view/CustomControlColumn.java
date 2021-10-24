package SmartBord.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CustomControlColumn extends VBox implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private GridPane columnGrid;

    @FXML
    private Label name;

    @FXML
    private MenuButton columnButton;

    @FXML
    private MenuItem add;

    @FXML
    private MenuItem deleteColumn;

    public CustomControlColumn() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomControlVbox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbox.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.COPY));
    }


    public MenuItem getDeleteColumn() {
        return deleteColumn;
    }

    public void setDeleteColumn(MenuItem deleteColumn) {
        this.deleteColumn = deleteColumn;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public GridPane getColumnGrid() {
        return columnGrid;
    }

    public void setColumnGrid(GridPane columnGrid) {
        this.columnGrid = columnGrid;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public MenuButton getColumnButton() {
        return columnButton;
    }

    public void setColumnButton(MenuButton columnButton) {
        this.columnButton = columnButton;
    }

    public MenuItem getAdd() {
        return add;
    }

    public void setAdd(MenuItem add) {
        this.add = add;
    }
}
