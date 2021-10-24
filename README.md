## myA2 ##
This is the repo for FP for A2 in Semester 2, 2021
<br/>
Pengcheng Lin s3664976
video：
https://web.microsoftstream.com/video/bbde87ad-8ae4-4fe1-b83f-9decc660659d
## make sure： ##
Make sure you have mysql workbench installed
<br/>
src/main/java/SmartBord/model/dao/DatabaseConnection.java ：
<br/>
Please make sure there is a "jdbctest" database in the database
<br/>
Change the username and password in the code to make sure they belong to your current computer.
<br/>
	
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


## report ##
1. How did you apply MVC design pattern to build this application?
I implemented this mvc by creating different packages:
<br/>
The Model：
The model defines the data that the application should contain. If the state of these data changes, the model usually notifies the view (so the display can be changed as needed) and sometimes the controller (if different logic is needed to control the updated view).
<br/>
The View：
The view defines how the app's data should be displayed
<br/>
The Controller<br/>
The controller contains logic that updates the model and/or view in response to input from the users of the app.
<br/>
How does your code adhere to SOLID design principles?<br/>
A class should only do one thing, and a class should only have one reason for change. For example, the user class only cares about the information that the user should have.

