package lab.contract.biz.allbuilding.building_register.controller.api;

import lab.contract.biz.allbuilding.building_register.controller.dto.request.BuildingUploadRequestDto;
import lab.contract.biz.allbuilding.building_register.service.BuildingRegisterService;
import lab.contract.biz.allbuilding.building_register_img.service.BuildingRegisterImgService;
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

    @PostMapping("/file/building-register")
    public ResponseEntity fileUpload(
            BuildingUploadRequestDto buildingUploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = buildingRegisterService.saveBuildingRegister(buildingUploadRequestDto.getUserId(), buildingUploadRequestDto.getContractId());
        String fileName = buildingRegisterService.saveBuildingRegisterPdfFile(buildingUploadRequestDto.getPdfFile());
        buildingRegisterImgService.convertPdfToPng(fileName);
        buildingRegisterImgService.saveBuildingRegisterImg(saveId, fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
