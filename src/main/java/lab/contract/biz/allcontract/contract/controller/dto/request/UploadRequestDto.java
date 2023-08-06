
package lab.contract.biz.allcontract.contract.controller.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class UploadRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}