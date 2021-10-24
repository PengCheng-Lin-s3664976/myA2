package SmartBord.model.dao;



import SmartBord.exception.SmartBordException;
import SmartBord.model.user.RegularUser;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final String TABLE_NAME = "new_table";

    public UserDaoImpl() {
    }

    @Override
    public void setup() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();) {
//            String sql1 ="DROP TABLE IF EXISTS " + TABLE_NAME;
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + " (userName VARCHAR(45) NOT NULL,"
                    + "image BLOB,"
                    + "firstName varchar(45),"
                    + "lastName varchar(45),"
                    + "password VARCHAR(45) NOT NULL,"
                    + "PRIMARY KEY (userName))";
//            stmt.execute(sql1);
            int result = stmt.executeUpdate(sql);
            if (result==0){
                System.out.println(TABLE_NAME+ " table created");
            }
        }
    }

    @Override
    public RegularUser getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userName = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                   Blob  imageBlob = rs.getBlob("image");
                    InputStream InputStream =  imageBlob.getBinaryStream();
                    return new RegularUser(
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            InputStream
                            );
                }
                return null;
            } catch (SmartBordException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(String.valueOf(e));
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
                return null;
            }
        }
    }
    @Override
    public InputStream getUserImage(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userName = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))  {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Blob  imageBlob = rs.getBlob("image");
                    InputStream InputStream =  imageBlob.getBinaryStream();
                    return InputStream;
                }
                return null;
            }
        }
    }

    @Override
    public boolean updateUser(InputStream image, String username, String password, String registerFirstName, String registerLastName) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET image = ?, firstName = ?, lastName = ?"+ "WHERE userName = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))  {
            stmt.setBinaryStream(1, image, image.available());
            stmt.setString(2,registerFirstName);
            stmt.setString(3,registerLastName);
            stmt.setString(4,username);
            stmt.executeUpdate();

            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(String.valueOf(e));
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
            return false;
        }
    }

    @Override
    public RegularUser createUser(InputStream image, String username, String password, String registerFirstName, String registerLastName) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))  {
            stmt.setString(1,username);
            stmt.setBinaryStream(2, image, image.available());
            stmt.setString(3,registerFirstName);
            stmt.setString(4,registerLastName);
            stmt.setString(5,password);
            stmt.executeUpdate();

            return new RegularUser(username,password,registerFirstName,registerLastName,image);
        } catch (IOException | SmartBordException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(String.valueOf(e));
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
            return null;
        }
    }
}
