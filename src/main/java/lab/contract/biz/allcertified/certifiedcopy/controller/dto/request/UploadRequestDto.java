
package lab.contract.biz.allbuilding.building_register.controller.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class UploadRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}