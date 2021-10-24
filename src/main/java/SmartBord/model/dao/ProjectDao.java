package SmartBord.model.dao;

import SmartBord.model.project.ProjectSystem;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.*;

public class ProjectDao {

    private final String TABLE_NAME = "project";

    public ProjectDao() {

    }

    public void setup() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();) {
//            String sql1 ="DROP TABLE IF EXISTS " + TABLE_NAME;
//            String sql2 = "SET SQL_SAFE_UPDATES = 0";
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + " (userName varchar(45) NOT NULL,"
                    + "projectData BLOB NOT NULL,"
                    + "defaultTab varchar(45),"
                    + "defaultTabCheck varchar(10),"
                    + "PRIMARY KEY (userName))";
//            stmt.execute(sql1);
//            stmt.execute(sql2);
            int result = stmt.executeUpdate(sql);
            if (result==0){
                System.out.println(TABLE_NAME+ " table created");
            }
        }
    }
    public boolean updateProject( String username,int defaultTabCheck, int defaultTab) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET projectData = ?, defaultTab = ?, defaultTabCheck = ?"+ "WHERE userName = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))  {
            FileInputStream fis = new FileInputStream("src/main/resources/Projects.txt");
            stmt.setBinaryStream(1, fis, fis.available());
            stmt.setString(2, String.valueOf(defaultTab));
            stmt.setString(3, String.valueOf(defaultTabCheck));
            stmt.setString(4,username);


            stmt.executeUpdate();
            fis.close();
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
    public ProjectSystem getProject(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userName = ?";
        ProjectSystem projectSystem;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
             stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Blob  imageBlob = rs.getBlob("projectData");
                    InputStream InputStream =  imageBlob.getBinaryStream();
                    ObjectInputStream ois = new ObjectInputStream(InputStream);

                    projectSystem =(ProjectSystem)ois.readObject();
                    return projectSystem;
                }
                return null;
            } catch (IOException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(String.valueOf(e));
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
                return null;
            }
        }
    }

    public String getDefaultTabCheck(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("defaultTabCheck");
                }
                return null;
            }
        }
    }

    public String getDefaultTab(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("defaultTab");
                }
                return null;
            }
        }
    }

    public void createProject(String username, int defaultTabCheck, int defaultTab) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))  {
            FileInputStream fis = new FileInputStream("src/main/resources/Projects.txt");
            stmt.setString(1,username);
            stmt.setBinaryStream(2, fis, fis.available());
            stmt.setString(3, String.valueOf(defaultTab));
            stmt.setString(4, String.valueOf(defaultTabCheck));
            stmt.executeUpdate();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
