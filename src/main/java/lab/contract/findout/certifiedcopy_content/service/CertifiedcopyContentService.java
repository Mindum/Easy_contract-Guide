package lab.contract.findout.certifiedcopy_content.service;

import org.springframework.stereotype.Service;

@Service
public class CertifiedcopyContentService {
    static String fullText;

    public void saveCertifiedcopyContent(Long contractId, String text) {
        fullText = text;
    }
    public String[] findOutContent() {
        String totalAddress;
        String address;
        String streetAddress;
        String registerPurpose;
        String etc;

    }
}
