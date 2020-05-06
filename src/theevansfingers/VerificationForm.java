package theevansfingers;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.*;
import database.Database;
import java.awt.Frame;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import logic.HoldVariables;
import logic.Identity;
import logic.Students;


public class VerificationForm extends CaptureForm {
    
    
    public interface Listener { void onVerify(Students s, Identity id); }
    final Listener listener;

    public VerificationForm(Frame owner, Listener listener) {
        super(owner);
        this.listener = listener;
    }
    
    //
   
    @Override
    protected void init() {
        super.init();
        this.setTitle("Fingerprint Verification");
        updateStatus(0);
        runThis();
    }

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);
        
        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {
            try {
                for (Identity i : Identity.loadAll()) {
                    System.out.println("Trying " + i.regNumber);
                    DPFPVerificationResult result = i.verify(sample);
                    System.err.println("FAR = " + result.getFalseAcceptRate());
                    if (result.isVerified()) {
                        makeReport("The fingerprint was VERIFIED.");
                         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                         LocalDateTime now = LocalDateTime.now();
                         String time = dtf.format(now);   
                         LocalDate today = LocalDate.now();
                         Database db = new Database();
                         String returnedValue;
                         if(HoldVariables.lateComers == null){
                           returnedValue = 
                                 db.registerStudentAttance(i.regNumber, HoldVariables.courseCode, 
                                         time, HoldVariables.manNumber ,String.valueOf(today),"Present");
                         }else {
                             System.out.print("Here My Guy");
                              returnedValue = 
                                 db.registerStudentAttance(i.regNumber, HoldVariables.courseCode, 
                                         time, HoldVariables.manNumber ,String.valueOf(today), "Late");
                         }
                    
                        	
                         if (returnedValue.equals("already")) {
                         	System.out.println("Student already registered");
                         }else if (returnedValue.equals("studentAdded")) {
                         	System.out.println("Registered");
                         }else {
                         	System.out.println("An error took place");
                         }
                        
                        
//                        if (listener != null)
//                            listener.onVerify(Student.load(i.regNumber), i);                        
                        return;
                    }
                }
                makeReport("THE FINGERPRINT WAS NOT VERIFIED!");
                JOptionPane.showMessageDialog(null, 
                            "THE FINGERPRINT WAS NOT VERIFIED!",
                            "VERIFICATION ERROR",
                            JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
    
       public void runThis(){
         
           Runnable runnable = () -> {};
           
             ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

            final ScheduledFuture updateLog =  service.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.SECONDS);
            service.schedule(new Runnable() {
                @Override
                public void run() {
                   if(HoldVariables.lateComers == null){
                       HoldVariables.lateComers = "Start This Shit";
                   }else {
                       HoldVariables.lateComers = null;
                   }
                    System.out.println("Close the window in "+HoldVariables.countDownTime);
                    
                    service.shutdown();
                    System.out.println(service.isShutdown());
                    closeWindow();
                }
            }, HoldVariables.countDownTime, TimeUnit.SECONDS);
  
           }
       
    
    //Override the method 
    @Override
    public void closeWindow(){
        setVisible(false);
    }

    private void updateStatus(int FAR) {
        // Show "False accept rate" value
        setStatus(String.format("False Accept Rate (FAR) = %1$s", FAR));
    }
    
    
   
}
