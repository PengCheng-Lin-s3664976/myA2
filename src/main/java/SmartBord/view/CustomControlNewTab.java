package SmartBord.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CustomControlNewTab extends Tab{

    @FXML
    private Tab newTab;

    @FXML
    private ScrollPane scroll;

    @FXML
    private HBox hbox;



    public CustomControlNewTab() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewTabPage.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

    }

    public Tab getNewTab() {
        return newTab;
    }

    public void setNewTab(Tab newTab) {
        this.newTab = newTab;
    }

    public ScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(ScrollPane scroll) {
        this.scroll = scroll;
    }

    public HBox getHbox() {
        return hbox;
    }

    public void setHbox(HBox hbox) {
        this.hbox = hbox;
    }
}
