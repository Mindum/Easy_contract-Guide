package lab.contract.allcertified.certifiedcopy_img.service;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedcopyRepository;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImg;
import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImgRepository;
<<<<<<< HEAD
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.openapi.clovaocr.TemplateOCR;
import lab.contract.openapi.convert.ConvertAPI;
import lab.contract.s3.S3UploadService;
=======
import lab.contract.openapi.convert.ConvertAPI;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
=======

import java.io.File;
import java.io.IOException;
import java.util.Optional;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class CertifiedcopyImgService {
    private final CertifiedcopyRepository certifiedcopyRepository;
    private final CertifiedcopyImgRepository certifiedcopyImgRepository;
    private final ConvertAPI convertAPI;
<<<<<<< HEAD
    private final TemplateOCR templateOCR;
    private final S3UploadService s3UploadService;
    private final ContractImgService contractImgService;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
    }
}
