package lab.contract.biz.contract_img.service;

import lab.contract.biz.contract.persistence.entity.Contract;
import lab.contract.biz.contract.persistence.repository.ContractRepository;
import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
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
public class ContractImgService {

    private final ContractImgRepository contractImgRepository;
    private final ContractRepository contractRepository;
    private final ConvertAPI convertAPI;

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convert(pdfFileName);
    }
    public int savaContractImg(Long contractId, String pdfFileName) throws IOException {
        Optional<Contract> contract = contractRepository.findById(contractId);
        File source = new File("C:/contract/getpdf/"+pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();
        for (int i=1; i<=pagesOfPdf; i++) {
            if (i==1) {
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url("C:/contract/getpdf/"+pdfFileName).build());
            } else {
                contractImgRepository.save(ContractImg.builder()
                        .contract(contract.get())
                        .page(i)
                        .url("C:/contract/getpdf/" + pdfFileName + "_" + i).build());
            }
        }
        return pagesOfPdf;
    }

}
