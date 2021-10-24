package SmartBord.model.user;

import SmartBord.exception.SmartBordException;
import SmartBord.model.project.Project;

import java.util.ArrayList;

public interface InterfaceUser{
    boolean addProject(String projectName) throws SmartBordException;
    ArrayList<Project> getProjects();
}
