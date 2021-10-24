## myA2 ##
This is the repo for FP for A2 in Semester 2, 2021
<br/>
Pengcheng Lin s3664976

## make sure： ##
Make sure you have mysql workbench installed
src/main/java/SmartBord/model/dao/DatabaseConnection.java ：
Please make sure there is a "jdbctest" database in the database
Change the username and password in the code to make sure they belong to your current computer.
	
	public class DatabaseConnection {
		private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbctest";

		public static Connection getConnection() throws SQLException {
			return DriverManager.getConnection(DB_URL,"root","1234567890q");
		}
	}


## lib folder： ##
  have javafx 17 and mysql-connector
  <br/>
  
  
  **You must first set the path of these two files：**

  forjavafx see here：
  https://openjfx.io/openjfx-docs/

  for mysql see here：
  https://www.youtube.com/watch?v=zGnL-LIFT9Y&t=3s

  Run the test each time to ensure that the path is saved in Run->Edit Configuration in the top navigation bar.
  The test files in this file are installed through **maven**, so maven must be used.
  <br/>
## src-> main -> java -> SmartBord -> main folder： ##
  There is a start file：StartApplication.java
 
## src-> main -> java -> SmartBord -> controller folder： ##
Is the controller of fxml

## src-> main -> java -> SmartBord -> model folder： ##
It is the place where the back-end stores information and connects to the database

## src-> main -> java -> SmartBord -> view folder： ##
This is the place to customize custom controls

## src/main/resources/ ##
Where to store fxml or other data

## src/test/java/SmartBord/model/ ##
Where to test the file
