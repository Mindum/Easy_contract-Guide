package lab.contract.biz.contract_img.service;

import lab.contract.biz.contract.persistence.entity.Contract;
import lab.contract.biz.contract.persistence.repository.ContractRepository;
<<<<<<< HEAD
import lab.contract.biz.contract.service.ContractService;
=======
>>>>>>> master
import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.openapi.convert.ConvertAPI;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;
=======
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
>>>>>>> master
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractImgService {

    private final ContractImgRepository contractImgRepository;
    private final ContractRepository contractRepository;
    private final ConvertAPI convertAPI;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }
<<<<<<< HEAD
    public int saveContractImg(Long contractId, String pdfFileName) throws IOException {
=======
    public int savaContractImg(Long contractId, String pdfFileName) throws IOException {
>>>>>>> master

        Optional<Contract> contract = contractRepository.findById(contractId);
        if (!contract.isPresent()) {
            // contract 예외 처리
        }
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName).build());
            } else {
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName + "_" + i).build());
            }
        }
        return pagesOfPdf;
    }
<<<<<<< HEAD
}
=======

}
>>>>>>> master
