package lab.contract.biz.allcertified.certifiedcopy.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CertifiedRequestDto {
    private Long contractId;
    private MultipartFile pdfFile;
}
