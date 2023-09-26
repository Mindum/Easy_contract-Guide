package lab.contract.allcertified.certifiedcopy_img.service;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedcopyRepository;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImg;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImgRepository;
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.openapi.clovaocr.TemplateOCR;
import lab.contract.openapi.convert.ConvertAPI;
import lab.contract.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final S3UploadService s3UploadService;
    private final ContractImgService contractImgService;
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
        String[] url = new String[pagesOfPdf];
        String name;

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName + ".png";
                MultipartFile multipartFile = contractImgService.transferMultipart(name);
                String s3url = s3UploadService.putPngFile(multipartFile);
                url[i-1] = s3url;
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            } else {
                name = pdfFileName + "-" + i + ".png";
                MultipartFile multipartFile = contractImgService.transferMultipart(name);
                String s3url = s3UploadService.putPngFile(multipartFile);
                url[i-1] = s3url;
                certifiedcopyImgRepository.save(CertifiedcopyImg.builder()
                        .certifiedcopy(certifiedcopy)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            }
        }
        content.add(0,url);
        /**
         * 디버그
         */
        for (int i=0;i<content.size();i++) {
            System.out.println("{\"" + content.get(i)[0] +"\",\"" +content.get(i)[1] +"\"},");
        }
        return content;
    }
}
