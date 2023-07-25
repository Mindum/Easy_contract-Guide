package lab.contract.biz.contract.controller;

import lab.contract.biz.contract.service.ContractService;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(originPatterns = "*")
@RestController
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/fileupload")
    public ResponseEntity fileupload(
            UploadRequestDto uploadRequestDto) throws IOException {
        Long saveId = contractService.saveContract(uploadRequestDto.getUserId());
        contractService.savePdfFile(uploadRequestDto.getPdfFile());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS,"save contract id :"+saveId), HttpStatus.OK);
    }
}
