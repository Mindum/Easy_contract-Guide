package lab.contract.allcertified.certifiedcopy.controller;

import lab.contract.allcertified.certifiedcopy.service.CertifiedcopyService;
import lab.contract.allcertified.certifiedcopy_img.service.CertifiedcopyImgService;
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

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class CertifiedcopyController {

    private final CertifiedcopyImgService certifiedcopyImgService;
    private final CertifiedcopyService certifiedcopyService;
    @PostMapping("/file/certifiedcopy")
    public ResponseEntity buildingRegisterUpload(
            CertifiedRequestDto certifiedRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = certifiedcopyService.saveCertifiedcopy(certifiedRequestDto.getContractId());
        String fileName = certifiedcopyService.savePdfFile(certifiedRequestDto.getPdfFile());
        certifiedcopyImgService.convertPdfToPng(fileName);
        certifiedcopyImgService.saveCertifiedcopyImg(saveId,fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
