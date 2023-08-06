package lab.contract.biz.allbuilding.building_register.controller.dto.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingUploadRequestDto {
    private Long userId;
    private Long contractId;
    private MultipartFile pdfFile;
}
