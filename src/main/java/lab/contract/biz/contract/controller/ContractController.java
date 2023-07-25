package lab.contract.biz.contract.controller;

import lab.contract.biz.contract.service.ContractService;
import lab.contract.biz.contract_img.service.ContractImgService;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractImgService contractImgService;

    @PostMapping("/fileupload")
    public ResponseEntity fileupload(
            UploadRequestDto uploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(uploadRequestDto.getUserId());
        String fileName = contractService.savePdfFile(uploadRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS,"save contract id :"+saveId), HttpStatus.OK);
    }
}
