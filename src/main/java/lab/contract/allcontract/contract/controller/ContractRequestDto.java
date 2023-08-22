
package lab.contract.allcontract.contract.controller;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ContractRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}