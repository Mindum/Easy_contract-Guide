package lab.contract.allbuilding.building_register.service;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allbuilding.building_register.persistence.BuildingRegisterRepository;
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
public class BuildingRegisterService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final BuildingRegisterRepository buildingRegisterRepository;

    private static final String UPLOAD_PATH = "C:/contract/getpdf/";

    public Long saveBuildingRegister(Long contractId){
        Optional<Contract> contract = contractRepository.findById(contractId);
        BuildingRegister saveBuildingRegister = BuildingRegister.builder()
                .contract(contract.get())
                .build();
        buildingRegisterRepository.save(saveBuildingRegister);

        return saveBuildingRegister.getId();
    }

    public String saveBuildingRegisterPdfFile(MultipartFile pdfFile) throws IOException {
        String pdfFileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        File saveFile = new File(UPLOAD_PATH, pdfFileName);
        pdfFile.transferTo(saveFile);
        return pdfFileName;
    }
}
