package lab.contract.analysis_result.result.controller;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.compare.ContractCompareService;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result.service.AllResultService;
import lab.contract.analysis_result.result_field.persistence.CertifiedResultField;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.service.ResultFieldService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AllResultController {
    private final ContractRepository contractRepository;
    private final ContractCompareService contractCompareService;
    private final AllResultService allResultService;
    private final ResultFieldService resultFieldService;

    @PostMapping("/result/contract")
    public ResponseEntity contractResult(
            ContractResultRequestDto contractResultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long contractId = contractResultRequestDto.getContractId();
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        if (contract.getAll_result() == null) {
            allResultService.saveAllResult(contractId);
        }
        List<ResultField> list = contract.getAll_result().getResult_field();
        if (list.isEmpty()) {
            contractCompareService.saveContractComment(contractId);
            System.out.println("계약서 결과 리스트 비어있음");
        }
        int rate = contract.getAll_result().getRate();
        ContractResultResponseDto resultResponseDto = ContractResultResponseDto.builder()
                .rate(rate)
                .resultFields(list.toArray())
                .build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, resultResponseDto), HttpStatus.OK);
    }
    @PostMapping("/result/building-register")
    public ResponseEntity buildingRegisterResult(
            ContractResultRequestDto contractResultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long contractId = contractResultRequestDto.getContractId();
        allResultService.saveAllResult(contractId);
        resultFieldService.saveBuildingRegisterResult(contractId);
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        List<ResultField> list = contract.getAll_result().getBuilding_register_result();
        /**
         * 전체 (계약서 등기부 건축물) 합계 점수로 업데이트되도록 수정해야함
         */
        int rate = contract.getAll_result().getRate();
        ContractResultResponseDto resultResponseDto = ContractResultResponseDto.builder()
                .rate(rate)
                .resultFields(list.toArray())
                .build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, resultResponseDto), HttpStatus.OK);
    }
    @PostMapping("/result/certifiedcopy")
    public ResponseEntity certifiedcopyResult(
            CertifiedResultRequestDto certifiedResultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long contractId = certifiedResultRequestDto.getContractId();
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        List<CertifiedResultField> list = contract.getAll_result().getCertifiedcopy_result();
        if (list.isEmpty()) {
            resultFieldService.saveCertifiedcopyResult(contractId);
        }
        int rate = contract.getAll_result().getRate();
        CertifiedResultResponseDto resultResponseDto = CertifiedResultResponseDto.builder()
                .rate(rate)
                .resultFields(list.toArray())
                .build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, resultResponseDto), HttpStatus.OK);
    }
}
