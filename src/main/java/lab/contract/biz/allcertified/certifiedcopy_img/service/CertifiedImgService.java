package lab.contract.biz.allcertified.certifiedcopy_img.service;

import lab.contract.biz.allbuilding.building_register_img.persistence.entity.BuildingImg;
import lab.contract.biz.allbuilding.building_register_img.persistence.repository.BulidingImgRepository;
import lab.contract.biz.allcertified.certifiedcopy.persistence.entity.Certified;
import lab.contract.biz.allcertified.certifiedcopy.persistence.repository.CertifiedRepository;
import lab.contract.biz.allcertified.certifiedcopy_img.persistence.entity.CertifiedImg;
import lab.contract.biz.allcertified.certifiedcopy_img.persistence.repository.CertifiedImgRepository;
import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import lab.contract.biz.allcontract.contract.persistence.repository.ContractRepository;
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

    private final CertifiedImgRepository certifiedImgRepository;
    private final CertifiedRepository certifiedRepository;
    private final ConvertAPI convertAPI;
    private static final String UPLOAD_PATH = "C:/biz/certified/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/biz/certified/savepng/";


    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }
    public int savaContractImg(Long contractId, String pdfFileName) throws IOException {

        Optional<Certified> certified = certifiedRepository.findById(certifiedId);
        if (!certified.isPresent()) {
            // contract 예외 처리
        }
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                certifiedImgRepository.save(CertifiedImg.builder()
                        .certified(certified.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName).build());
            } else {
                certifiedImgRepository.save(BuildingImg.builder()
                        .contract(certified.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName + "_" + i).build());
            }
        }
        return pagesOfPdf;
    }

}
