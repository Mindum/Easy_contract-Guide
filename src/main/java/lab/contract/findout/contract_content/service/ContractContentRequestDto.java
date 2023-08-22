package lab.contract.findout.contract_content.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractContentRequestDto {
    private Contract contract;
}
