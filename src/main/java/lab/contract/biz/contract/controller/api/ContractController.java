package lab.contract.biz.contract.controller.api;

import lab.contract.biz.contract.controller.dto.request.UploadRequestDto;
import lab.contract.biz.contract.service.ContractService;
import lab.contract.biz.contract_img.service.ContractImgService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final ContractImgService contractImgService;

    @PostMapping("/fileupload")
    public ResponseEntity fileUpload(
            UploadRequestDto uploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(uploadRequestDto.getUserId());
        String fileName = contractService.savePdfFile(uploadRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
        contractImgService.savaContractImg(saveId,fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
