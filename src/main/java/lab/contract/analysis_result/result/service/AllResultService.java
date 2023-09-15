package lab.contract.analysis_result.result.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result.persistence.AllResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AllResultService {
    private final ContractRepository contractRepository;
    private final AllResultRepository allResultRepository;

    public Long saveAllResult (Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        AllResult allResult = AllResult.builder().contract(contract).build();
        contract.setAllResult(allResult);
        return allResultRepository.save(allResult).getId();
    }

}
