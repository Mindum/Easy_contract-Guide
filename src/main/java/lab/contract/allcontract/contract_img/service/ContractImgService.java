package lab.contract.allcontract.contract_img.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract_img.persistence.ContractImg;
import lab.contract.allcontract.contract_img.persistence.ContractImgRepository;
import lab.contract.openapi.clovaocr.GeneralOCR;
import lab.contract.openapi.convert.ConvertAPI;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractImgService {

    private final ContractImgRepository contractImgRepository;
    private final ContractRepository contractRepository;
    private final ConvertAPI convertAPI;
    private final GeneralOCR generalOCR;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public List<String> saveContractImg(Long contractId, String pdfFileName) throws IOException {

        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);

        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        StringBuilder content = new StringBuilder();
        String name;
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName +".png";
                //MultipartFile multipartFile = transferMultipart(name);
                //String s3Url = s3UploadService.putPngFile(multipartFile);
                ContractImg saveContractImg = contractImgRepository.save(ContractImg.builder()
                        .contract(contract)
                        .page(i)
                        .url(DOWNLOAD_PATH+name).build());
                contract.addContractImg(saveContractImg);
                content.append(generalOCR.ocrapi(name));
            } else {
                name = pdfFileName + "-" + i +".png";
                //MultipartFile multipartFile = transferMultipart(name);
                //String s3Url = s3UploadService.putPngFile(multipartFile);
                ContractImg saveContractImg = contractImgRepository.save(ContractImg.builder()
                        .contract(contract)
                        .page(i)
                        .url(DOWNLOAD_PATH+name).build());
                contract.addContractImg(saveContractImg);
                content.append(generalOCR.ocrapi(name));
            }
        }
        contract.setContract_text(content.toString());

        return list;
    }
/*
    public MultipartFile transferMultipart(String name) throws IOException {
        File file = new File(DOWNLOAD_PATH + name);

        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

 */
}


