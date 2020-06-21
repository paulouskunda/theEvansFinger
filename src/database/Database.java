
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import logic.Attendance;
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
    private static Database handler = null;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private FileInputStream hold = null;
    private int holdLin;

    public Database() {
        getCon();
    }

    public static Database getInstance() {
        if (handler == null) {
            handler = new Database();
        }
        return handler;
    }

    public static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/theEvansFinger", "root", "");
            return con;
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

//    , BufferedImage image
    public boolean insertStudent(String values[]) {
        boolean check = false;

        try {
            String sql = "INSERT INTO STUDENTINFORM(studentname, studentnumber, gender,   program, dob,yearofstudy) "
                    + "VALUES (?, ?, ?, ?,?, ?)";
            statement = con.prepareStatement(sql);

            int num = 1;

            for (int i = 1; i <= values.length; i++) {
                statement.setString(i, values[i - 1]);
                num++;
            }

            statement.execute();
            check = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean insertLecturer(String values[]) {
        boolean check = false;

        try {
            String sql = "INSERT INTO lecturer(fullName, mannumber, gender,  username, password) "
                    + "VALUES (?, ?, ?, ?,?)";
            statement = con.prepareStatement(sql);

            int num = 1;

            for (int i = 1; i <= values.length; i++) {
                statement.setString(i, values[i - 1]);
                num++;
            }

            //statement.set
            statement.execute();
            check = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean searchStudent(String studentNumber) {
        boolean check = false;
        try {
            String sql = "SELECT * FROM studentinform WHERE studentNumber = ?";
            statement = con.prepareStatement(sql);

            statement.setString(1, studentNumber);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.print(resultSet.getString(2));
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean insertFinger(DPFPTemplate template, String studentNumber) {
        boolean check = false;
        ByteArrayInputStream byteArray = new ByteArrayInputStream(template.serialize());
        Integer len = template.serialize().length;

        try {
            String sql = "INSERT INTO fingerprint(studentNumber, finger) "
                    + "VALUES (?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, studentNumber);
            statement.setBinaryStream(2, byteArray, len);

            statement.execute();

            check = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }

    /**
     * Get all fingers register to the system.
     *
     * @return
     */
    public List<Identity> getAllFinger() {
        List<Identity> identityList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM fingerprint";
            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //Trying passing it in byte form
                byte[] finger = resultSet.getBytes("finger");
                String studentNumber = resultSet.getString("studentNumber");
                DPFPTemplate temp2 = DPFPGlobal.getTemplateFactory().createTemplate();
                temp2.deserialize(finger);
                Identity identity = new Identity(studentNumber, temp2);
                identityList.add(identity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return identityList;
    }

    /**
     * Register student attendance list
     *
     * @param studentNumber
     * @param courseCode
     * @param timeAttended
     * @param mannumber
     * @param dateOfClass
     * @return
     */
    public String registerStudentAttance(String studentNumber, String courseCode, 
            String timeAttended, String mannumber, String dateOfClass, String status) {
        String check = "false";

        try {
            //Select the student exists in the system with data
            String SQLSelect = "SELECT * FROM attendance "
                    + "WHERE studentNumber = ? And courseCode=? And dateOfClass=?";

            statement = con.prepareStatement(SQLSelect);
            statement.setString(1, studentNumber);
            statement.setString(2, courseCode);
            statement.setString(3, dateOfClass);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return "already";
            } else {
                String sql = "INSERT INTO attendance(studentNumber, courseCode,timeAttend,"
                        + " mannumber, dateOfClass, status) VALUES (?,?,?,?,?,?) ";
                statement = con.prepareStatement(sql);

                statement.setString(1, studentNumber);
                statement.setString(2, courseCode);
                statement.setString(3, timeAttended);
                statement.setString(4, mannumber);
                statement.setString(5, dateOfClass);
                statement.setString(6, status);
                

                statement.execute();

                return "studentAdded";
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "false";
        }

    }

    /**
     * This is a method for inserting into the class table of the database
     *
     * @param courseCode
     * @param dateOfClass
     * @return
     */
    public boolean insertIntoClass(String courseCode, String dateOfClass) {

        try {
            String sqlSelect = "SELECT * FROM class WHERE courseCode = ? and dateOfClass = ?";
            statement = con.prepareStatement(sqlSelect);
            statement.setString(1, courseCode);
            statement.setString(2, dateOfClass);
            
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.print("Class for today started way back");
                return true;
            }else {
                String sqlInsert = "INSERT INTO class(courseCode, dateOfClass) VALUES (?, ?)";
                statement = con.prepareStatement(sqlInsert);
                statement.setString(1, courseCode);
                statement.setString(2, dateOfClass);
                
                statement.execute();
                
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    /**
     * Add Course Method
     * @param courseName
     * @param courseCode
     * @return 
     */
    public boolean addCourse(String courseName, String courseCode) {
        boolean check = false;
        try {
            String sql = "INSERT INTO courses(course_name, course_code) VALUES (?,?) ";
            statement = con.prepareStatement(sql);

            statement.setString(1, courseName);
            statement.setString(2, courseCode);

            statement.execute();

            check = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    /**
     * Register courses for a given student
     * @param values
     * @param studentNumber
     * @return 
     */
    public boolean registerCourses(String values[], String studentNumber) {
        boolean check = false;

        try {
            String sql = "INSERT INTO coursestaken(course_code, studentNumber) "
                    + "VALUES (?, ?)";
            statement = con.prepareStatement(sql);

            /*
             * Loop through the array and add the values to the database
             */
            for (int i = 1; i <= values.length; i++) {
                statement.setString(1, values[i - 1]);
                statement.setString(2, studentNumber);
                statement.execute();
            }

            check = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean searchFinger() {
        return false;
    }

    /**
     * Get Students Information
     * @return 
     */
    public List<Students> studentInfo() {
        List<Students> studentList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM studentInform";

            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Students student = new Students();
                student.setStudentName(resultSet.getString("studentName"));
                student.setStudentNumber(resultSet.getString("studentNumber"));
                student.setYearOfStudy(resultSet.getString("yearOfStudy"));
                student.setProgram(resultSet.getString("program"));
                student.setGender(resultSet.getString("gender"));

                studentList.add(student);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public List<PassableVariables> genderChart() {
        List<PassableVariables> gender = new ArrayList<>();

        try {
            //Select the number of student 
            String sql = "Select (Select count(*) from studentinform where gender='Male') AS Male,"
                    + "(Select count(*) from studentinform where gender='Female') AS Female";

            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getInt(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gender;
    }
    
    /**
     * Login method
     * @param username
     * @param password
     * @return 
     */

    public String loginDetails(String username, String password) {
        try {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return "trueAdmin";
            } else {
                String sqlLecturer = "SELECT * FROM lecturer WHERE username = ? AND password = ?";
                statement = con.prepareStatement(sqlLecturer);
                statement.setString(1, username);
                statement.setString(2, password);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return "trueLecturer";
                } else {
                    return "WrongLoginDetails";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An Error occured";
        }

    }
    
    /**
     * Get All courses in the system
     * @return 
     */
    public List<Courses> getAllCourses() {
        List<Courses> coursesList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM courses";
            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Courses courses = new Courses();
                courses.setCourseName(resultSet.getString("course_name"));
                courses.setCourseCode(resultSet.getString("course_code"));
                coursesList.add(courses);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coursesList;
    }

    public List<Lecturer> getAllLecturer() {
        List<Lecturer> lecturerList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM lecturer";
            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerName(resultSet.getString("fullName"));
                lecturer.setManNumber(resultSet.getString("mannumber"));
                lecturerList.add(lecturer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lecturerList;
    }

    public List<Lecturer> getAllLecturer(String username) {
        List<Lecturer> lecturerList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM lecturer WHERE username = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerName(resultSet.getString("fullName"));
                lecturer.setManNumber(resultSet.getString("mannumber"));
                lecturerList.add(lecturer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lecturerList;
    }

    //Get all students count for the attendance
    public List<Students> getAllStudentAttendance(String studentNumber) {
        List<Students> studentList = new ArrayList<>();

        try {
            String sql = "SELECT courseCode, COUNT(courseCode) as Total "
                    + " FROM attendance WHERE studentNumber = ?"
                    + " GROUP BY courseCode ORDER BY COUNT(courseCode)";
            statement = con.prepareStatement(sql);
            System.out.println(studentNumber);
            statement.setString(1, studentNumber);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Students student = new Students();
                student.setCourseName(resultSet.getString("courseCode"));
                student.setTotalClassAttended(resultSet.getInt("Total"));
                studentList.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public List<Students> getAllCourseAttendance(String course) {
        List<Students> studentList = new ArrayList<>();

        try {
            String sql = "SELECT studentinform.studentNumber, studentinform.studentName, COUNT(attendance.studentNumber) "
                    + "AS Total, attendance.courseCode FROM studentinform "
                    + "LEFT JOIN  attendance ON studentinform.studentNumber = attendance.studentNumber "
                    + "WHERE attendance.courseCode = ? "
                    + "GROUP BY studentinform.studentName, attendance.courseCode";
            statement = con.prepareStatement(sql);
            statement.setString(1, course);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Students student = new Students();
                student.setStudentNumber(resultSet.getString("studentNumber"));
                student.setStudentName(resultSet.getString("studentName"));
                student.setCourseName(resultSet.getString("courseCode"));
                student.setTotalClassAttended(resultSet.getInt("Total"));
                studentList.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }

    //Get all courses  count 
    public List<Courses> getAllCourseCount() {
        List<Courses> courseList = new ArrayList<>();

        try {
            String sql = "SELECT courseCode, COUNT(class.courseCode) as Total "
                    + "FROM class "
                    + "GROUP BY class.courseCode ORDER BY COUNT(class.courseCode)";
            statement = con.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Courses course = new Courses();
                course.setCourseName(resultSet.getString("courseCode"));
                course.setTotalCourse(resultSet.getInt("Total"));
                courseList.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseList;
    }
    /**
     * 
     * @param number
     * @return 
     */
    public List<Courses> getManNumber(String number) {
        List<Courses> courseList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT(courseCode) FROM attendance WHERE mannumber = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, number);
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Courses course = new Courses();
                course.setCourseName(resultSet.getString("courseCode"));
                courseList.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseList;
    }

    /**
     * 
     * @param manNumber
     * @param courseCode
     * @param dateOfClass
     * @return 
     */
    public List<Attendance> getAllAttendance(String manNumber, String courseCode, String dateOfClass) {
        List<Attendance> attendanceList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM attendance, studentinform "
                    + "WHERE manNumber = ? AND courseCode = ? AND dateOfClass = ? "
                    + "AND attendance.studentNumber = studentinform.studentNumber";
            statement = con.prepareStatement(sql);
            statement.setString(1, manNumber);
            statement.setString(2, courseCode);
            statement.setString(3, dateOfClass);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Attendance attend = new Attendance();
                attend.setCourseName(resultSet.getString("courseCode"));
                attend.setStudentName(resultSet.getString("studentName"));
                attend.setStudentNumber(resultSet.getString("studentNumber"));
                attend.setLateComingStatus(resultSet.getString("status"));
                attendanceList.add(attend);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return attendanceList;
    }
    
    /**
     * 
     * @param studentNumber
     * @param reason
     * @param courseCode
     * @return 
     */
    public String updateTheLateComers(String studentNumber, String reason, String courseCode){
        LocalDate today = LocalDate.now();

        String sqlUpdate = "UPDATE `attendance` SET comment = ? "
                + "WHERE studentNumber = ? AND dateOfClass = ? AND courseCode = ? AND status = ?";
        
        try{
            statement = con.prepareStatement(sqlUpdate);
            statement.setString(1, reason);
            statement.setString(2, studentNumber);
            statement.setString(3, String.valueOf(today));
            statement.setString(4, courseCode);
            statement.setString(5, "Late");
            
            statement.execute();
            
            return "Updated";
        }catch(Exception e){
            e.printStackTrace();
            return "Error";
        }

        
    }
}
