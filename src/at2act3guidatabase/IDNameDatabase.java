package at2act3guidatabase;
/**
 * @author 30024165 Sang Joon Lee
 * AT2 – Practical - Activity 3
 * 010/11/2020
 */
import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IDNameDatabase extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AT2 Practical Activity3 Database with GUI"); // set title
        // GridPane is for columns and rows grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER); // grid settings
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Find Name"); // Welcome title
        sceneTitle.setFont(Font.font("Century Gothic", FontWeight.BOLD, 20));
        sceneTitle.setTextAlignment(TextAlignment.CENTER);
        grid.add(sceneTitle, 0, 0);

        Label instruction = new Label("Enter ID and Click to find name");
        instruction.setTextAlignment(TextAlignment.CENTER);
        grid.add(instruction, 0, 1);
        
        TextField num = new TextField();
        num.setPrefSize(20, 30);
        num.setAlignment(Pos.CENTER);
        grid.add(num, 0, 2);
        
        // Button
        Button button = new Button("Find");
        HBox hBox = new HBox(10);
        hBox.setAlignment((Pos.CENTER));
        hBox.getChildren().add(button);
        grid.add(hBox, 0, 3);
        
        final Text messageText = new Text();
        messageText.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
        messageText.setTextAlignment(TextAlignment.CENTER);
        grid.add(messageText, 0, 5);
        
        // Event Handler
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int id = Integer.parseInt(num.getText());
                    messageText.setFill(Color.NAVY);
                    String name = getName(id);
                    messageText.setText("ID " + id + ": " + name);
                } catch (Exception ex) {
                    messageText.setFill(Color.RED);
                    messageText.setText("Invalid Input. Integer please.");
                }
                
            }
        });

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";

        Connection con = null; // JDBC connection
        Statement stmt = null; // SQL statement object
        String query; // SQL query string
        ResultSet result = null; // results after SQL execution
        
        try {
            con = DriverManager.getConnection(url, user, password); // connect to MySQL
            stmt = con.createStatement();
            
            // Select and Use database
            query = "USE IDNameDatabase;";
            stmt.executeUpdate(query);
            System.out.println("now IDName Database is on ✔");
            // Display data from table
            query = "SELECT * FROM IDName;";
            result = stmt.executeQuery(query);

            System.out.printf("%8s %-11s\n",
                    "ID", "Name");
            while (result.next()) { // loop until the end of the results
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("%7d  %-8s\n",
                        id, name);
            }
            
        } catch (Exception ex) {
            System.out.println("SQLException caught: " + ex.getMessage());
        } finally {
            // Close all database objects nicely
            try {
                if (result != null) {
                    result.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException caught: " + ex.getMessage());
            }
        }
        
        launch(args);
    }
    
    public String getName(int id) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";
        String name = " No Exist";

        Connection con = null; // JDBC connection
        Statement stmt = null; // SQL statement object
        String query; // SQL query string
        ResultSet result = null; // results after SQL execution
        
        try {
            con = DriverManager.getConnection(url, user, password); // connect to MySQL
            stmt = con.createStatement();
            
            query = "USE IDNameDatabase;";
            stmt.executeUpdate(query);
//            System.out.println("now IDName Database is on ✔");
            
            query = "SELECT name FROM IDName WHERE id = " + id;
            result = stmt.executeQuery(query);
            while (result.next()) { // loop until the end of the results
                name = result.getString("name");
            }
            
            
        } catch (Exception ex) {
            System.out.println("SQLException caught: " + ex.getMessage());
        } finally {
            // Close all database objects nicely
            try {
                if (result != null) {
                    result.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException caught: " + ex.getMessage());
            }
        }
            
        return name;
    }
}
