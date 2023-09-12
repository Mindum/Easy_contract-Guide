package lab.contract.allcontract.contract.controller;

import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.findout.contract_content.service.ContractContentService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final ContractImgService contractImgService;
    private final ContractContentService contractContentService;

    @PostMapping("/file/contract")
    public ResponseEntity fileUpload(
            ContractRequestDto contractRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(contractRequestDto.getUserId());
        String fileName = contractService.savePdfFile(contractRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
        List<String> list = contractImgService.saveContractImg(saveId,fileName);
        Object[] url = new Object[list.size()];
        for (int i=0;i< url.length;i++) {
            url[i] = list.get(i);
        }
        //contractContentService.saveContractContent(saveId);
        ContractUploadResponse contractUploadResponse = ContractUploadResponse.builder()
                .contractId(saveId)
                .pages(url.length)
                .urls(url).build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, contractUploadResponse), HttpStatus.OK);
    }
}
