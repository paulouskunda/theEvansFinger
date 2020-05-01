/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theevansfingers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.Database;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import logic.Courses;
import logic.Students;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Paulous
 */
public class Reports extends javax.swing.JFrame {

    /**
     * Creates new form Reports
     */
    public Reports() {
        initComponents();
    }
    
    /**
     * Method that loads all students from
     */
    
    private void loadStudents(){
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeOfReport = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        studentNumber = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        typeOfReport.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Students", "All courses", "Courses against Student" }));
        typeOfReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeOfReportActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel1.setText("GENERATE REPORTS");

        studentNumber.setText("Student Number");
        studentNumber.setToolTipText("Student Number");
        studentNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentNumberActionPerformed(evt);
            }
        });

        jButton1.setText("Generate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");

        jButton3.setText("STUDENTS ABOVE 85%");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(229, 229, 229)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(studentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(118, 118, 118)
                                .addComponent(typeOfReport, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(typeOfReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(studentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton1)))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeOfReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeOfReportActionPerformed
        // TODO add your handling code here:
        
        
        
        
    }//GEN-LAST:event_typeOfReportActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String student = studentNumber.getText();
        
        Database db = new Database();
        
        if(db.searchStudent(student)){
            printIndividualReport(student);
        }else {
            System.out.print("Nigga");
        }


//        try {
//            // TODO add your handling code here:
//
//            printAllStudents();
//        } catch (Exception ex) {
//            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_jButton3ActionPerformed

    private void studentNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentNumberActionPerformed
        // TODO add your handling code here:
    
        
    }//GEN-LAST:event_studentNumberActionPerformed
    
    private void printIndividualReport(String studentNumber) {
        
        //Get the database result
        Database db = new Database();
        
        List<Students> students = db.getAllStudentAttendance(studentNumber);
        List<Courses> courses = db.getAllCourseCount();
        HashMap<String, Integer> studentHash = new HashMap();
        HashMap<String, Integer> courseHash = new HashMap();
       
        
        //for loop to itterate through the list
        for(int i =0; i<students.size(); i++){
            
            String courseName = students.get(i).getCourseName();
            int totalCount = students.get(i).getTotalClassAttended();
            studentHash.put(courseName, totalCount);
        }
        
        //hash map for  course
          //for loop to itterate through the list
        for(int i =0; i<courses.size(); i++){
            
            String courseName = courses.get(i).getCourseName();
            int totalCount = courses.get(i).getTotalCourse();
            courseHash.put(courseName, totalCount);
        }
        
        
        //Start the pdf
       //Create the database object
         JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(new File("StudentIform.pdf"));
            int dialogResult = dialog.showSaveDialog(null);
            if (dialogResult==JFileChooser.APPROVE_OPTION){
            try {
                String filePath = dialog.getSelectedFile().getPath();
                
                
                //Create the document
                
                Document document = new Document();
                PdfWriter myWritter = PdfWriter.getInstance(document, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(5);
                document.open();
                
                float[] columnWidths =new float[] {2,2,2,2,2};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                
                document.add(new Paragraph("=========================ALL STUDENTS INFORMATION======================= "));
                document.add(new Paragraph("\n"));
                table.addCell(new PdfPCell(new Paragraph("STUDENT ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Course Code",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Total",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Total Classes",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Percentage of Atttendance",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));

                //Get the iterator
                
                Iterator passThroughMe = studentHash.entrySet().iterator();
                
                while(passThroughMe.hasNext()){
                    int i = 0;
                    Map.Entry mapElement = (Map.Entry)passThroughMe.next();
                   System.out.println(courseHash.get(mapElement.getKey()));
                   
                    
                    float counts = (int)mapElement.getValue();
                    float countCourses = courseHash.get(mapElement.getKey());
                    table.addCell(new PdfPCell(new Paragraph( studentNumber ,FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(mapElement.getKey()) ,FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(counts),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(countCourses),
                            FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
                   
                   // the percentage of attendance 
                   float percent = (counts/countCourses) * 100;
                   System.out.println(percent);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(percent + "%"),
                            FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
                    i++;
                }

                document.add(table);
                
                //Create a pie chart for the gender
                
                document.close();
                
                System.out.println("Nice");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    private void printAllStudents() throws Exception{
           //Create the database object
         JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(new File("StudentIform.pdf"));
            int dialogResult = dialog.showSaveDialog(null);
            if (dialogResult==JFileChooser.APPROVE_OPTION){
            String filePath = dialog.getSelectedFile().getPath();
            
            Database db = new Database();
            List<Students> student = db.studentInfo();

            //Create the document 

            Document document = new Document();
            PdfWriter myWritter = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            PdfPTable table = new PdfPTable(6);
            document.open();
            
            float[] columnWidths =new float[] {2,2,2,2,2,2};
            table.setWidths(columnWidths);
            table.setWidthPercentage(100);
            
            document.add(new Paragraph("=========================ALL STUDENTS INFORMATION======================= "));
            document.add(new Paragraph("\n"));
            table.addCell(new PdfPCell(new Paragraph("STUDENT ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("STUDENT NAME",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("PROGRAM",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("YEAR OF STUDY", FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("GENDER", FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("DATE OF BIRTH", FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            
            for(int i =0; i< student.size(); i++){
             table.addCell(new PdfPCell(new Paragraph( student.get(i).getStudentName() ,FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(student.get(i).getStudentNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(student.get(i).getProgram(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(student.get(i).getYearOfStudy(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(student.get(i).getGender(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(student.get(i).getDob(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             
            }
          document.add(table);
          
          //Create a pie chart for the gender 
         
          document.close();
            
        }
    }
    
    private JFreeChart generatePieChart(){
        DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("China", 19.64);
		dataSet.setValue("India", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);

		JFreeChart chart = ChartFactory.createPieChart(
				"World Population by countries", dataSet, true, true, false);

		return chart;
    }
    
    
    private void getAllCourses() throws Exception {
            JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(new File("AllCourses.pdf"));
            int dialogResult = dialog.showSaveDialog(null);
            if (dialogResult==JFileChooser.APPROVE_OPTION){
            String filePath = dialog.getSelectedFile().getPath();
            
            
              Database db = new Database();
            
             List<Courses> courses = db.getAllCourses();

            //Create the document 

            Document document = new Document();
            PdfWriter myWritter = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            PdfPTable table = new PdfPTable(2);
            document.open();
            
            float[] columnWidths =new float[] {2,2};
            table.setWidths(columnWidths);
            table.setWidthPercentage(100);
            
            document.add(new Paragraph("=========================ALL COURSES======================= "));
            table.addCell(new PdfPCell(new Paragraph("COURSE NAME",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("COURSE CODE",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
            
            
            for(int i =0; i< courses.size(); i++){
             table.addCell(new PdfPCell(new Paragraph( courses.get(i).getCourseName() ,FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             table.addCell(new PdfPCell(new Paragraph(courses.get(i).getCourseCode(),FontFactory.getFont(FontFactory.TIMES_ROMAN,8,Font.PLAIN))));
             
            }
            document.add(table);
            document.close();
            
            
            }
      
    }
    
    
    private void attendance(){
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reports().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField studentNumber;
    private javax.swing.JComboBox typeOfReport;
    // End of variables declaration//GEN-END:variables
}
