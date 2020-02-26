/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.digitalpersona.onetouch.DPFPTemplate;
import com.mysql.jdbc.Connection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

/**
 *
 * @author Paulous
 */
public class Database {
    private static Connection con = null;
    private  static Database handler = null;
       private PreparedStatement statement;
    private ResultSet resultSet;
    private FileInputStream hold = null;
    private int holdLin;
    public Database(){
        getCon();
    }

    public static Database getInstance() {
        if (handler == null) {
            handler = new Database();
        }
        return handler;
    }

//    public DatabaseHandler(){
//
//    }

    public static Connection getCon(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
             con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/theevansfinger","root","");
            return con;
        } catch (SQLException e){
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    
//    , BufferedImage image
    public boolean insertStudent(String values[]){
        boolean check = false;
       
        try {
            String sql = "INSERT INTO STUDENTINFORM(studentname, studentnumber, gender,   program, dob,yearofstudy) "
                    + "VALUES (?, ?, ?, ?,?, ?)";
            statement = con.prepareStatement(sql);
            
            int num = 1;
            
            for(int i=1; i<=values.length;i++){
                statement.setString(i, values[i-1]);
                num++;
            }
            
            //statement.set
            
            
            statement.execute();
            check = true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return check;
    }
    
    public boolean insertLecturer(String values[]){
        boolean check = false;
        
        try {
            String sql = "INSERT INTO lecturer(fullName, mannumber, gender,  username, password) "
                    + "VALUES (?, ?, ?, ?,?)";
            statement = con.prepareStatement(sql);
            
            int num = 1;
            
            for(int i=1; i<=values.length;i++){
                statement.setString(i, values[i-1]);
                num++;
            }
            
            //statement.set
            
            
            statement.execute();
            check = true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return check;
    }
    
    public boolean searchStudent(String studentNumber){
        boolean check = false;
        try{
            String sql = "SELECT * FROM studentinform WHERE studentNumber = ?";
            statement = con.prepareStatement(sql);
            
            statement.setString(1, studentNumber);
            
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                 System.out.print(resultSet.getString(2));
                 check = true;
            }
           
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }
    
    public boolean insertFinger( DPFPTemplate template, String studentNumber){
       boolean check = false;
         ByteArrayInputStream byteArray = new ByteArrayInputStream(template.serialize());
            Integer len = template.serialize().length;
            
            try{
               String sql = "INSERT INTO fingerprint(studentNumber, finger) "
                    + "VALUES (?, ?)";
               
            statement = con.prepareStatement(sql);
            statement.setString(1, studentNumber);
            statement.setBinaryStream(2,byteArray, len);
            
            
            statement.execute();
            
            check = true;
            }catch(Exception e){
                e.printStackTrace();
            }
        
            return check;
    }
    
    public boolean registerCourses(String values[], String studentNumber){
        boolean check = false;
        
        try {
            String sql = "INSERT INTO coursestaken(course_code, studentNumber) "
                    + "VALUES (?, ?)";
            statement = con.prepareStatement(sql);
            
            /*
            * Loop through the array and add the values to the database
            */
            for(int i=1; i<=values.length;i++){
                statement.setString(1, values[i-1]);
                statement.setString(2, studentNumber);
                statement.execute();
            }
            
            
            
            check = true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return check;
    }
    
    public boolean searchFinger(){
        return false;
    }
}


