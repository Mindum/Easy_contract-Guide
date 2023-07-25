package lab.contract.biz.contract.service;

import lab.contract.biz.contract.persistence.entity.Contract;
import lab.contract.biz.contract.persistence.repository.ContractRepository;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.presentation.repository.UserRepository;
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

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public Long saveContract(
            Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Contract saveContract = Contract.builder()
                .user(user.get())
                .contract_name("untitled")
                .build();
        contractRepository.save(saveContract);
        return saveContract.getId();
    }
    public void savePdfFile(MultipartFile pdfFile) throws IOException {
        String pdfFileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        String path = "C:/JavaStudy/pdfFile/";
        File saveFile = new File(path, pdfFileName);
        pdfFile.transferTo(saveFile);

    }
}
