package lab.contract.biz.contract_img.service;

import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.openapi.convert.ConvertAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractImgService {

    private final ContractImgRepository contractImgRepository;
    private final ConvertAPI convertAPI;

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convert(pdfFileName);
    }

}
