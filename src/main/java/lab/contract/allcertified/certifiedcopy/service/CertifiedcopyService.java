package lab.contract.allcertified.certifiedcopy.service;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedcopyRepository;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CertifiedcopyService {
    private final ContractRepository contractRepository;
    private final CertifiedcopyRepository certifiedcopyRepository;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";

    public Long saveCertifiedcopy(Long contractId) {
        Optional<Contract> contract = contractRepository.findById(contractId);
        Certifiedcopy saveCertifiedcopy = Certifiedcopy.builder()
                .contract(contract.get())
                .build();
        return certifiedcopyRepository.save(saveCertifiedcopy).getId();
    }
    public String savePdfFile(MultipartFile pdfFile) throws IOException {
        String pdfFileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        File saveFile = new File(UPLOAD_PATH, pdfFileName);
        pdfFile.transferTo(saveFile);
        return pdfFileName;
    }
}
