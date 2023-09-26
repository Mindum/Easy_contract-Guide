package lab.contract.analysis_result.result.controller;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.service.AllResultService;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.service.BuildingRegisterCompareService;
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
public class BuildingRegisterResultController {
    private final BuildingRegisterCompareService buildingRegisterCompareService;
    private final AllResultService allResultService;
    private final ContractRepository contractRepository;

    @PostMapping("/result/building-register")
    public ResponseEntity result(
            BuildingRegisterResultRequestDto buildingRegisterResultRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long contractId = buildingRegisterResultRequestDto.getContractId();
        allResultService.saveAllResult(contractId);
        buildingRegisterCompareService.saveBuildingRegisterComment(contractId);
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        List<ResultField> list = contract.getAll_result().getBuilding_register_result();
        BuildingRegisterResultResponseDto buildingRegisterResultResponseDto = BuildingRegisterResultResponseDto.builder()
                .resultFields(list.toArray())
                .build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, buildingRegisterResultResponseDto), HttpStatus.OK);
    }
}
