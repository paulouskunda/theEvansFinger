/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import database.Database;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Paulous
 */
public class Identity {
    private final DPFPVerification verificator
            = DPFPGlobal.getVerificationFactory().createVerification();

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory()
                .createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }
    
    public final String regNumber;
    public final DPFPTemplate fingerprint;

    public Identity(String regNumber, DPFPTemplate fingerprint) {
        this.regNumber = regNumber;
        this.fingerprint = fingerprint;
    }
    
     public DPFPVerificationResult verify(DPFPSample sample) {
        DPFPFeatureSet features = extractFeatures(sample,
                DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        return verificator.verify(features, fingerprint);
    }
     
     
     
       public static List<Identity> loadAll() throws IOException {
           Database database = new Database();
        return database.getAllFinger();
    }

    public DPFPVerification getVerificator() {
        return verificator;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public DPFPTemplate getFingerprint() {
        return fingerprint;
    }
}
