package lab.contract.allcontract.contract.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.contract.FileSizeLimitExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public Long saveContract(Long userId, MultipartFile pdfFile){
        checkFileSize(pdfFile);

        Optional<User> user = userRepository.findById(userId);
        Contract saveContract = Contract.builder()
                .user(user.get())
                .contract_name("untitled")
                .build();
        contractRepository.save(saveContract);
        return saveContract.getId();
    }

    public String savePdfFile(MultipartFile pdfFile) throws IOException {
        checkFileSize(pdfFile);

        String pdfFileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        File saveFile = new File(UPLOAD_PATH, pdfFileName);
        pdfFile.transferTo(saveFile);
        return pdfFileName;
    }

    public void checkFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileSizeLimitExceededException("FileSizeLimitExceededException", ResponseMessage.FILE_SIZE_LIMIT);
        }
    }

}
