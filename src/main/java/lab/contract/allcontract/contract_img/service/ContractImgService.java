package lab.contract.allcontract.contract_img.service;

<<<<<<< HEAD
=======

>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract_img.persistence.ContractImg;
import lab.contract.allcontract.contract_img.persistence.ContractImgRepository;
import lab.contract.openapi.clovaocr.GeneralOCR;
import lab.contract.openapi.convert.ConvertAPI;
<<<<<<< HEAD
import lab.contract.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
=======
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractImgService {

    private final ContractImgRepository contractImgRepository;
    private final ContractRepository contractRepository;
    private final ConvertAPI convertAPI;
    private final GeneralOCR generalOCR;
<<<<<<< HEAD
    private final S3UploadService s3UploadService;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }
<<<<<<< HEAD
    public List<String> saveContractImg(Long contractId, String pdfFileName) throws IOException {

        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
=======
    public String saveContractImg(Long contractId, String pdfFileName) throws IOException {

        Optional<Contract> contract = contractRepository.findById(contractId);
        if (!contract.isPresent()) {
            // contract 예외 처리
        }
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();
        StringBuilder content = new StringBuilder();
        String name;
<<<<<<< HEAD
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName +".png";
                MultipartFile multipartFile = transferMultipart(name);
                String s3Url = s3UploadService.putPngFile(multipartFile);
                list.add(s3Url);
                ContractImg saveContractImg = contractImgRepository.save(ContractImg.builder()
                        .contract(contract)
                        .page(i)
                        .url(s3Url).build());
                contract.addContractImg(saveContractImg);
                content.append(generalOCR.ocrapi(name));
            } else {
                name = pdfFileName + "-" + i +".png";
                MultipartFile multipartFile = transferMultipart(name);
                String s3Url = s3UploadService.putPngFile(multipartFile);
                list.add(s3Url);
                ContractImg saveContractImg = contractImgRepository.save(ContractImg.builder()
                        .contract(contract)
                        .page(i)
                        .url(s3Url).build());
                contract.addContractImg(saveContractImg);
                content.append(generalOCR.ocrapi(name));
            }
        }
        contract.setContract_text(content.toString());

        return list;
    }

    public MultipartFile transferMultipart(String name) throws IOException {
        File file = new File(DOWNLOAD_PATH + name);

        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
=======
        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName +".png";
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url(DOWNLOAD_PATH+name).build());
                content.append(generalOCR.ocrapi(name));
            } else {
                name = pdfFileName + "-" + i +".png";
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url(DOWNLOAD_PATH+name).build());
                content.append(generalOCR.ocrapi(name));
            }
        }
        contract.get().update(content.toString());

        return content.toString();
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
    }

}
