package SmartBord.model.user;


import SmartBord.exception.SmartBordException;
import SmartBord.model.project.Project;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RegularUser implements InterfaceUser, Serializable {

    protected String username;
    protected String password;
    protected String registerFirstName;
    protected String registerLastName;
    protected InputStream image;
    protected ArrayList<Project> projects;

    public RegularUser(String username, String password, String registerFirstName, String registerLastName, InputStream image) throws SmartBordException {
        if (username == null || username.isEmpty() || username.isBlank()){
            throw new SmartBordException("username is null or empty");
        }
        if (password == null || password.isEmpty() || username.isBlank()){
            throw new SmartBordException("password is null or empty");
        }
        if (registerFirstName == null || registerFirstName.isEmpty() || registerFirstName.isBlank()){
            throw new SmartBordException("registerFirstName is null or empty");
        }
        if (registerLastName == null || registerLastName.isEmpty() || registerLastName.isBlank()){
            throw new SmartBordException("registerLastName is null or empty");
        }
        if (image == null ){
            throw new SmartBordException("image is null or empty");
        }
        this.username = username;
        this.password = password;
        this.registerFirstName = registerFirstName;
        this.registerLastName = registerLastName;
        this.image = image;
        this.projects = new ArrayList<>();
    }

    public String getRegisterFirstName() {
        return registerFirstName;
    }

    public String getRegisterLastName() {
        return registerLastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRegisterFirstName(String registerFirstName) {
        this.registerFirstName = registerFirstName;
    }

    public void setRegisterLastName(String registerLastName) {
        this.registerLastName = registerLastName;
    }

    public boolean addProject(String projectName) throws SmartBordException {

        for (Project project:projects){
            if (projectName.equals(project.getProjectName())){
                return false;
            }
        }
        projects.add(new Project(projectName));
        return true;
    }

    public Boolean deleteProject(String projectName) {
        int i =0;
        for (Project project:projects){
            if (projectName.equals(project.getProjectName())){
                projects.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    public Project findProject(String projectName){

        for (Project project:projects){
            if (projectName.equals(project.getProjectName())){
                return project;
            }
        }
        return null;
    }

    public ArrayList<Project> getProjects(){
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
}
