package lab.contract.biz.contract.controller;

import lab.contract.biz.contract.persistence.service.ContractService;
import lab.contract.biz.contract_img.persistence.service.ContractImgService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractImgService contractImgService;

    @PostMapping(value = "/fileupload")
    public ResponseEntity fileUpload( UploadRequestDto uploadRequestDto) throws IOException, ExecutionException, InterruptedException {
            Long saveId = contractService.saveContract(uploadRequestDto.getUserId());
            String filename = contractService.savePdfFile(uploadRequestDto.getPdfFile());
            contractImgService.convertPdfToPng(filename);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS,"save contract id :"+saveId ), HttpStatus.OK);
    }
}