/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theevansfingers;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import logic.Identity;

/**
 *
 * @author Paulous
 */
public class VerifyFinger {
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();

    public VerifyFinger() {
        init();
//        DPFPSample sample = process1();
//        process();
    }
    
    
    
   public void init(){
       System.out.print("S");
    capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override public void readerConnected(final DPFPReaderStatusEvent e) {
				new Runnable() {
                                    public void run() {
//		 			makeReport("The fingerprint reader was connected.");
                                        System.out.print("The fingerprint reader was connected.");
                                    }
                                };
			}
			@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
				new Runnable() {
                                    public void run() {
					 System.out.print("The fingerprint reader was disconnected.");
                                    }
                                };
			}
		});
    }
    
   protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
       public  DPFPSample process1(DPFPSample sample){
//            drawPicture(convertSampleToBitmap1(sample));
            return sample;
        }
        
	protected void start()
	{
		capturer.startCapture();
		System.out.print("Using the fingerprint reader, scan your fingerprint.");
	}

	protected void stop()
	{
		capturer.stopCapture();
	}
        
   
    protected void process(DPFPSample sample) {
//        super.process(sample);
        
        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {
            try {
                for (Identity i : Identity.loadAll()) {
                    System.out.println("Trying " + i.regNumber);
                    DPFPVerificationResult result = i.verify(sample);
                    System.err.println("FAR = " + result.getFalseAcceptRate());
                    if (result.isVerified()) {
//                        makeReport("The fingerprint was VERIFIED.");
                        JOptionPane.showMessageDialog(null, "FINGERPRINT  WAS VERIFIED");
//                        if (listener != null)
//                            listener.onVerify(Student.load(i.regNumber), i);                        
                        return;
                    }
                }
//                makeReport("THE FINGERPRINT WAS NOT VERIFIED!");
                JOptionPane.showMessageDialog(null, 
                            "THE FINGERPRINT WAS NOT VERIFIED!",
                            "VERIFICATION ERROR",
                            JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
