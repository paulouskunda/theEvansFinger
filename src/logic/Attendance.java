/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Paulous
 */
public class Attendance {
    private String courseName;
    private String studentName;
    private String studentNumber;
    private String lateComingStatus;
   

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getLateComingStatus() {
        return lateComingStatus;
    }

    public void setLateComingStatus(String lateComingStatus) {
        this.lateComingStatus = lateComingStatus;
    }
    
}
