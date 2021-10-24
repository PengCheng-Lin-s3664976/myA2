package SmartBord.model.project;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectSystem implements Serializable {
    ArrayList<Project>list;

    public ProjectSystem() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Project> getList() {
        return list;
    }

    public void setList(ArrayList<Project> list) {
        this.list = list;
    }
}
