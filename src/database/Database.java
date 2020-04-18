/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.digitalpersona.onetouch.DPFPGlobal;
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
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import logic.Courses;
import logic.Identity;
import logic.Lecturer;
import logic.PassableVariables;
import logic.Students;

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


    public static Connection getCon(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
             con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/finalyearproject","root","");
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
    
    public  List<Identity> getAllFinger(){
        List<Identity> identityList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM fingerprint";
            statement = con.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                //Trying passing it in byte form
                byte[] finger = resultSet.getBytes("finger");
                String studentNumber = resultSet.getString("studentNumber");
                DPFPTemplate temp2 = DPFPGlobal.getTemplateFactory().createTemplate();
                temp2.deserialize(finger);
                Identity identity = new Identity(studentNumber,temp2  );
                identityList.add(identity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return identityList;
    }
     public String registerStudentAttance(String studentNumber, String courseCode, String timeAttended, String mannumber, String dateOfClass){
        String check = "false";
        
        
        
        try{
            
            //Select the student exists in the system with data
            
            String SQLSelect = "SELECT * FROM attendance WHERE studentNumber = ? AND courseCode = ? AND dateOfClass = ?";
            
            statement = con.prepareStatement(SQLSelect);
            statement.setString(1, studentNumber);
            statement.setString(2, courseCode);
            statement.setString(3, dateOfClass);
            
            resultSet = statement.executeQuery();
           
            if(resultSet.next()){
                return "already";
            }else {
                String sql = "INSERT INTO attendance(studentNumber, courseCode,timeAttend, dateOfClass, mannumber) VALUES (?,?,?,?,?) ";
                statement = con.prepareStatement(sql);

                statement.setString(1, studentNumber);
                statement.setString(2, courseCode);
                statement.setString(3, timeAttended);
                statement.setString(4, dateOfClass);
                statement.setString(5, mannumber);

                statement.execute();
            
                return "studentAdded";
            }

            
            
         
        }catch(Exception e){
            e.printStackTrace();
            
            return "false";
        }
      
    }
    
    public boolean registerStudentAttance(){
        return false;
    }
    
    public boolean addCourse(String courseName, String courseCode){
        boolean check = false;
        try{
            String sql = "INSERT INTO courses(course_name, course_code) VALUES (?,?) ";
            statement = con.prepareStatement(sql);
            
            statement.setString(1, courseName);
            statement.setString(2, courseCode);
            
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
    
    
    
    //Return a result for all student 
    
    public List<Students> studentInfo(){
        List<Students> studentList = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM studentInform";
            
            statement = con.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Students student = new Students();
                student.setStudentName(resultSet.getString("studentName"));
                student.setStudentNumber(resultSet.getString("studentNumber"));
                student.setYearOfStudy(resultSet.getString("yearOfStudy"));
                student.setProgram(resultSet.getString("program"));
                student.setGender(resultSet.getString("gender"));
                
                
                studentList.add(student);
                
            }
            
        }catch(Exception e) {
           e.printStackTrace();
        }
        
        return studentList;
    }
    
    public List<PassableVariables> genderChart(){
        List<PassableVariables> gender = new ArrayList<>();
        
        try{
            //Select the number of student 
            String sql = "Select (Select count(*) from studentinform where gender='Male') AS Male,"
                    + "(Select count(*) from studentinform where gender='Female') AS Female";
            
            statement = con.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));
            }

            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return gender;
    }
    
    public List<Courses> getAllCourses(){
        List<Courses> coursesList = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM courses";
            statement = con.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Courses courses = new Courses();
                courses.setCourseName(resultSet.getString("course_name"));
                courses.setCourseCode(resultSet.getString("course_code"));
                coursesList.add(courses);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return coursesList;
    } 
    public List<Lecturer> getAllLecturer(){
        List<Lecturer> lecturerList = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM lecturer";
            statement = con.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerName(resultSet.getString("fullName"));
                lecturer.setManNumber(resultSet.getString("mannumber"));
                lecturerList.add(lecturer);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lecturerList;
    }
    
    
   
}


