package lab.contract.analysis_result.result.controller;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.service.AllResultService;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.service.ContractCompareService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")  // 프론트엔드랑 연결할 때 필요
@RestController
@RequiredArgsConstructor
public class ContractResultController {
    private final ContractCompareService contractCompareService;
    private final AllResultService allResultService;
    private final ContractRepository contractRepository;

    @PostMapping("/result/contract")
    public ResponseEntity result(
            ContractResultRequestDto contractResultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long contractId = contractResultRequestDto.getContractId();
        allResultService.saveAllResult(contractId);
        contractCompareService.saveContractComment(contractId);
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        List<ResultField> list = contract.getAll_result().getResult_field();
        ContractResultResponseDto resultResponseDto = ContractResultResponseDto.builder()
                .resultFields(list.toArray())
                .build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, resultResponseDto), HttpStatus.OK);
    }

    /*
    @PostMapping("/result")
    public ResponseEntity result(
            ResultRequestDto resultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = resultRequestDto.getContractId();
        compareAddressService.saveCompareAddress(saveId);
        compareOwnerService.saveCompareOwner(saveId);
        compareSpecialOptionService.saveSpecialOption(saveId);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, saveId), HttpStatus.OK);
    }

     */
}
