package SmartBord.model.user;

import SmartBord.model.dao.ProjectDao;
import SmartBord.model.dao.UserDao;
import SmartBord.model.dao.UserDaoImpl;

import java.sql.SQLException;

public class UserModel {
    private UserDao userDao;
    private ProjectDao projectDao;
    private RegularUser currentUser;

    public UserModel() {
        userDao = new UserDaoImpl();
        projectDao = new ProjectDao();
    }

    public void setup() throws SQLException {
        userDao.setup();
        projectDao.setup();
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RegularUser getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(RegularUser user) {
        this.currentUser = user;
    }

}
