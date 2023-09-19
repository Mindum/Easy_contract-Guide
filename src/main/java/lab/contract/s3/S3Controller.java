package lab.contract.s3;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract_img.persistence.ContractImg;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class S3Controller {
    private final ContractRepository contractRepository;

    @PostMapping("/result")
    public ResponseEntity sendResult(@RequestParam Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        System.out.println("contractId = " + contract.getId());
        List<ContractImg> imgs = contract.getContract_imgs();
        System.out.println("imgs = " + imgs.get(0));
        int pageNum = imgs.size();
        Object url[] =  new Object[pageNum];
        for (int i=0;i<pageNum;i++) {
            url[i] = imgs.get(i).getUrl();
        }

        S3Response s3Response = S3Response.builder()
                .numOfPage(pageNum)
                .url(url).build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, s3Response),HttpStatus.OK);
    }
}
