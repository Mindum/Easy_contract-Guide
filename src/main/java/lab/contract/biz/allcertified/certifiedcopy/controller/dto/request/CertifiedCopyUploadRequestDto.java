package lab.contract.biz.allcertified.certifiedcopy.controller.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CertifiedCopyUploadRequestDto {
    private Long userId;
    private Long contractId;
    private MultipartFile pdfFile;
}
