package lab.contract.findout.contract_content.service;

import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")  // 프론트엔드랑 연결할 때 필요
@RestController
@RequiredArgsConstructor
public class ContractContentController {
    private final ContractContentService contractContentService;

    @PostMapping("/file/contract-content")
    public ResponseEntity contractContentInfo(
            ContractContentRequestDto contractContentRequestDto) throws IOException, ExecutionException, InterruptedException {
        contractContentService.saveContractContent(contractContentRequestDto.getContract());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }

}
