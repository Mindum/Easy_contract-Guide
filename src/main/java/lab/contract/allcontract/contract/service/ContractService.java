package lab.contract.allcontract.contract.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
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
public class ContractService {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";

    public Long saveContract(Long userId){
        Optional<User> user = userRepository.findById(userId);
        Contract saveContract = Contract.builder()
                .user(user.get())
                .contract_name("untitled")
                .build();
        contractRepository.save(saveContract);
        return saveContract.getId();
    }

    public String savePdfFile(MultipartFile pdfFile) throws IOException {
        String pdfFileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        File saveFile = new File(UPLOAD_PATH, pdfFileName);
        pdfFile.transferTo(saveFile);
        return pdfFileName;
    }
}
