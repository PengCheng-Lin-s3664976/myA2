package SmartBord.model.dao;


import SmartBord.model.user.RegularUser;

import java.io.InputStream;
import java.sql.SQLException;

public interface UserDao {
    void setup() throws SQLException;
    RegularUser getUser(String username, String password) throws SQLException;
    InputStream getUserImage(String username) throws SQLException;
    RegularUser createUser(InputStream image, String username, String password, String registerFirstName, String registerLastName) throws SQLException;
    boolean updateUser(InputStream image, String username, String password, String registerFirstName, String registerLastName) throws SQLException;
}
