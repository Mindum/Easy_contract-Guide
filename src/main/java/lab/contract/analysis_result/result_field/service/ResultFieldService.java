package lab.contract.analysis_result.result_field.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class ResultFieldService {
    private final ContractRepository contractRepository;

    public String checkLessor(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        ContractContent contractContent = contract.getContract_content();
        StringBuilder result = new StringBuilder();
        result.append(contractContent.getLessor_name()).append("\n");
        result.append(contractContent.getLessor_resident_number()).append("\n");
        result.append(contractContent.getLessor_address()).append("\n");
        result.append("계약서의 임대인이 등기부등본과 건축물대장의 소유자 정보와 빠짐없이 같은지 확인해보세요.\n");
        result.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.");

        return result.toString();
    }
}
