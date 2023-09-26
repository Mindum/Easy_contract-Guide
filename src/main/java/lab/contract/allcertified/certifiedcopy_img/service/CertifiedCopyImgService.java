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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private static final String jsonOutputPath = "C:/contract/content/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public ArrayList<String[]> saveCertifiedCopyImg(Long certifiedcopyId, String pdfFileName) throws IOException {

        CertifiedCopy certifiedCopy = certifiedCopyRepository.findById(certifiedcopyId).orElseThrow(EntityNotFoundException::new);
        //Optional<CertifiedCopy> certifiedCopy = certifiedCopyRepository.findById(certifiedcopyId);
        //if (!certifiedCopy.isPresent()) {
        //    // contract 예외 처리
        //}
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        ArrayList<String[]> content = new ArrayList<>();

        //int max = 30; // 최대 항목 수
        //String[][] ocr_content = new String[pagesOfPdf * max][2];
        //int resultIndex = 0; // 현재 결과 배열 인덱스

        //StringBuilder content = new StringBuilder();
        String name;

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName +".png";
                certifiedCopyImgRepository.save(CertifiedCopyImg.builder()
                        .certifiedCopy(certifiedCopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocrapi(name));
            } else {
                name = pdfFileName + "-" + i +".png";
                certifiedCopyImgRepository.save(CertifiedCopyImg.builder()
                        .certifiedCopy(certifiedCopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocrapi(name));
            }
            /*
            String[][] pageOcrContent = templateOCR.ocrapi(name);

            for (String[] item : pageOcrContent) {
                ocr_content[resultIndex][0] = item[0];
                ocr_content[resultIndex][1] = item[1];
                resultIndex++;
            }

             */
        }
        return content;
        //return ocr_content;
        //return content.toString();
    }

}
