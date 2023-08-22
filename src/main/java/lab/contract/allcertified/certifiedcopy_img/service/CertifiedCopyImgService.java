package lab.contract.allcertified.certifiedcopy_img.service;

import lab.contract.allcertified.certifiedcopy.persistence.CertifiedCopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedCopyRepository;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedCopyImg;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedCopyImgRepository;
import lab.contract.openapi.clovaocr.TemplateOCR;
import lab.contract.openapi.convert.ConvertAPI;
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
public class CertifiedCopyImgService {
    private final CertifiedCopyImgRepository certifiedCopyImgRepository;
    private final CertifiedCopyRepository certifiedCopyRepository;
    private final ConvertAPI convertAPI;
    private final TemplateOCR templateOCR;

    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public String saveCertifiedCopyImg(Long certifiedcopyId, String pdfFileName) throws IOException {

        Optional<CertifiedCopy> certifiedCopy = certifiedCopyRepository.findById(certifiedcopyId);
        if (!certifiedCopy.isPresent()) {
            // contract 예외 처리
        }
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        StringBuilder content = new StringBuilder();
        String name;

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName +".png";
                certifiedCopyImgRepository.save(CertifiedCopyImg.builder()
                        .certifiedCopy(certifiedCopy.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName).build());
                //content.append(templateOCR.templateOCR(name));
            } else {
                name = pdfFileName +".png";
                certifiedCopyImgRepository.save(CertifiedCopyImg.builder()
                        .certifiedCopy(certifiedCopy.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName + "_" + i).build());
                //content.append(templateOCR.templateOCR(name));
            }
        }
        return content.toString();
    }
}
