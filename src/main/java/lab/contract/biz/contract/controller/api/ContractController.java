package lab.contract.biz.contract.controller.api;

import lab.contract.biz.contract.controller.dto.request.UploadRequestDto;
import lab.contract.biz.contract.service.ContractService;
<<<<<<< HEAD
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
=======
>>>>>>> master
import lab.contract.biz.contract_img.service.ContractImgService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;
=======
>>>>>>> master

import java.io.IOException;
import java.util.concurrent.ExecutionException;

<<<<<<< HEAD
@CrossOrigin(originPatterns = "*")  // 프론트엔드랑 연결할 때 필요
@RestController
@RequiredArgsConstructor
public class ContractController {
    private final ContractImgService contractImgService;
    private final ContractService contractService;
=======
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final ContractImgService contractImgService;
>>>>>>> master

    @PostMapping("/fileupload")
    public ResponseEntity fileUpload(
            UploadRequestDto uploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(uploadRequestDto.getUserId());
        String fileName = contractService.savePdfFile(uploadRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
<<<<<<< HEAD
        contractImgService.saveContractImg(saveId,fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
=======
        contractImgService.savaContractImg(saveId,fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
>>>>>>> master
