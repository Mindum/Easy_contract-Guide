package lab.contract.analysis_result.result_field.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.compare.Compare;
import lab.contract.analysis_result.compare.ContractCompareService;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result_field.persistence.CertifiedResultField;
import lab.contract.analysis_result.result_field.persistence.CertifiedResultFieldRepository;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.persistence.ResultFieldRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.contract.IdNullException;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultFieldService {
    private final ResultFieldRepository resultFieldRepository;
    private final CertifiedResultFieldRepository certifiedResultFieldRepository;
    private final ContractRepository contractRepository;
    private final Compare compare;

    public void saveCertifiedcopyResult(Long contractId) {
        if (contractId == null) throw new IdNullException("IdNullException",ResponseMessage.ID_NULL);
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        AllResult allResult = contract.getAll_result();

        Compare.Result result1 = compare.compareAddressWithCertified(contract);
        CertifiedResultField resultField1 = CertifiedResultField.builder()
                .comment(result1.getComment())
                .type(result1.getResult())
                .allResult(allResult)
                .build();
        allResult.addCertifiedcopyResult(resultField1);
        certifiedResultFieldRepository.save(resultField1);

        Compare.Result result2 = compare.compareOwnerWithCertified(contract);
        CertifiedResultField resultField2 = CertifiedResultField.builder()
                .comment(result2.getComment())
                .type(result2.getResult())
                .allResult(allResult)
                .build();
        allResult.addCertifiedcopyResult(resultField2);
        certifiedResultFieldRepository.save(resultField2);

        Compare.Result result3 = compare.compareRegisterPurpose(contract);
        CertifiedResultField resultField3 = CertifiedResultField.builder()
                .comment(result3.getComment())
                .type(result3.getResult())
                .allResult(allResult)
                .build();
        allResult.addCertifiedcopyResult(resultField3);
        certifiedResultFieldRepository.save(resultField3);

        Compare.Result result4 = compare.compareMortgage(contract);
        CertifiedResultField resultField4 = CertifiedResultField.builder()
                .comment(result4.getComment())
                .type(result4.getResult())
                .allResult(allResult)
                .build();
        allResult.addCertifiedcopyResult(resultField4);
        certifiedResultFieldRepository.save(resultField4);
    }
    public void saveBuildingRegisterResult(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        AllResult allResult = contract.getAll_result();

        ResultField resultField1 = ResultField.builder()
                .build();
        allResult.addBuildingRegisterResult(resultField1);
        resultFieldRepository.save(resultField1);
    }

}
