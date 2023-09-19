package lab.contract.allcertified.certifiedcopy_img.service;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedcopyRepository;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImg;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImgRepository;
import lab.contract.openapi.clovaocr.TemplateOCR;
import lab.contract.openapi.convert.ConvertAPI;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class CertifiedcopyImgService {
    private final CertifiedcopyRepository certifiedcopyRepository;
    private final CertifiedcopyImgRepository certifiedcopyImgRepository;
    private final ConvertAPI convertAPI;
    private final TemplateOCR templateOCR;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public ArrayList<String[]> saveCertifiedcopyImg(Long certifiedcopyId, String pdfFileName) throws IOException {

        Certifiedcopy certifiedcopy = certifiedcopyRepository.findById(certifiedcopyId).orElseThrow(EntityNotFoundException::new);

        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();
        ArrayList<String[]> content = new ArrayList<>();
        String name;

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName + ".png";
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            } else {
                name = pdfFileName + "-" + i + ".png";
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            }
        }
        return content;
    }
}
