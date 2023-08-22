package lab.contract.allbuilding.building_register.controller;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingUploadRequestDto {
    private Long contractId;
    private MultipartFile pdfFile;
}
