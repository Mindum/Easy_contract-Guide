package lab.contract.allcertified.certifiedcopy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedCopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedCopyRepository;
import lab.contract.allcertified.certifiedcopy.service.CertifiedCopyService;
import lab.contract.allcertified.certifiedcopy_img.service.CertifiedCopyImgService;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedCopyContent;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedCopyContentRepository;
import lab.contract.findout.certifiedcopy_content.service.CertifiedCopyContentService;
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
    private final CertifiedCopyContentService certifiedCopyContentService;
    private final CertifiedCopyRepository certifiedCopyRepository;

    @PostMapping("/file/certifiedcopy")
    public ResponseEntity fileUpload(
            CertifiedCopyUploadRequestDto certifiedCopyUploadRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = certifiedCopyService.saveCertifiedCopy(certifiedCopyUploadRequestDto.getContractId());
        String fileName = certifiedCopyService.saveCertifiedCopyPdfFile(certifiedCopyUploadRequestDto.getPdfFile());
        certifiedCopyImgService.convertPdfToPng(fileName);

        certifiedCopyContentService.saveCertifiedCopyContent(saveId, certifiedCopyImgService.saveCertifiedCopyImg(saveId,fileName));

        //String JsonData[][] = certifiedCopyImgService.saveCertifiedCopyImg(saveId, fileName);

        //CertifiedCopy certifiedCopy = certifiedCopyRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
        //certifiedCopyContentService.saveCertifiedCopyContent(certifiedCopy, JsonData);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
