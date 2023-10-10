package lab.contract.allbuilding.building_register.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BuildingRequestDto {
    private Long contractId;
    private MultipartFile pdfFile;
}
