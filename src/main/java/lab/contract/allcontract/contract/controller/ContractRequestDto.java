package lab.contract.allcontract.contract.controller;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ContractRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}
