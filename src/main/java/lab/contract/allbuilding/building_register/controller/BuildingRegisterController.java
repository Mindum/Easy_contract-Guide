package lab.contract.allbuilding.building_register.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allbuilding.building_register.persistence.BuildingRegisterRepository;
import lab.contract.allbuilding.building_register.service.BuildingRegisterService;
import lab.contract.allbuilding.building_register_img.service.BuildingRegisterImgService;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.building_register_content.service.BuildingRegisterContentService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")  // 프론트엔드랑 연결할 때 필요
@RestController
@RequiredArgsConstructor
public class BuildingRegisterController {
    private final BuildingRegisterImgService buildingRegisterImgService;
    private final BuildingRegisterService buildingRegisterService;
    private final BuildingRegisterContentService buildingRegisterContentService;
    private final BuildingRegisterRepository buildingRegisterRepository;

    @PostMapping("/file/building-register")
    public ResponseEntity fileUpload(
            BuildingUploadRequestDto buildingUploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = buildingRegisterService.saveBuildingRegister(buildingUploadRequestDto.getContractId());
        String fileName = buildingRegisterService.saveBuildingRegisterPdfFile(buildingUploadRequestDto.getPdfFile());
        buildingRegisterImgService.convertPdfToPng(fileName);
        buildingRegisterContentService.saveBuildingRegisterContent(saveId, buildingRegisterImgService.saveBuildingRegisterImg(saveId, fileName));

        //String JsonData[][] = buildingRegisterImgService.saveBuildingRegisterImg(saveId, fileName);

        //BuildingRegister buildingRegister = buildingRegisterRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
        //buildingRegisterContentService.saveBuildingRegisterContent(buildingRegister, JsonData);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
