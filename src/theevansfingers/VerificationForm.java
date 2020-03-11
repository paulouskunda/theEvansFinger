package theevansfingers;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.*;
import java.awt.Frame;
import java.io.IOException;
import javax.swing.*;
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
                        JOptionPane.showMessageDialog(null, "FINGERPRINT  WAS VERIFIED");
                        
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
