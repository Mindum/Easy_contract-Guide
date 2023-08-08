package lab.contract.biz.allcertified.certifiedcopy_img.service;

import lab.contract.biz.allcertified.certifiedcopy.persistence.entity.Certifiedcopy;
import lab.contract.biz.allcertified.certifiedcopy.persistence.repository.CertifiedcopyRepository;
import lab.contract.biz.allcertified.certifiedcopy_img.persistence.enitty.CertifiedcopyImg;
import lab.contract.biz.allcertified.certifiedcopy_img.persistence.repository.CertifiedcopyImgRepository;
import lab.contract.biz.openapi.convert.ConvertAPI;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class CertifiedcopyImgService {
    private final CertifiedcopyRepository certifiedcopyRepository;
    private final CertifiedcopyImgRepository certifiedcopyImgRepository;
    private final ConvertAPI convertAPI;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public int saveCertifiedcopyImg(Long certifiedcopyId, String pdfFileName) throws IOException {

        Optional<Certifiedcopy> certifiedcopy = certifiedcopyRepository.findById(certifiedcopyId);
        if (!certifiedcopy.isPresent()) {
            // contract 예외 처리
        }
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName).build());
            } else {
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName + "_" + i).build());
            }
        }
        return pagesOfPdf;
    }
}
