package lab.contract.analysis_result.result_field.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class ResultFieldService {
    private final ContractRepository contractRepository;

}
