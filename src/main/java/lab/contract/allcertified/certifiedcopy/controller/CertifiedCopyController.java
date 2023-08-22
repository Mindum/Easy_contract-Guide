package lab.contract.allcertified.certifiedcopy.controller;

import lab.contract.allcertified.certifiedcopy.service.CertifiedCopyService;
import lab.contract.allcertified.certifiedcopy_img.service.CertifiedCopyImgService;
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
public class CertifiedCopyController {
    private final CertifiedCopyImgService certifiedCopyImgService;
    private final CertifiedCopyService certifiedCopyService;

    @PostMapping("/file/certifiedcopy")
    public ResponseEntity fileUpload(
            CertifiedCopyUploadRequestDto certifiedCopyUploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = certifiedCopyService.saveCertifiedCopy(certifiedCopyUploadRequestDto.getContractId());
        String fileName = certifiedCopyService.saveCertifiedCopyPdfFile(certifiedCopyUploadRequestDto.getPdfFile());
        certifiedCopyImgService.convertPdfToPng(fileName);
        certifiedCopyImgService.saveCertifiedCopyImg(saveId, fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
