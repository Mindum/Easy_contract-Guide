package lab.contract.analysis_result.result_field.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result_field.persistence.CertifiedResultField;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ResultFieldServiceTest {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ResultFieldService resultFieldService;

    @Test
    public void saveCertifiedcopyResult() {
        Long contractId = 1L;
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        resultFieldService.saveCertifiedcopyResult(contractId);
        List<CertifiedResultField> list = contract.getAll_result().getCertifiedcopy_result();
        if (list.isEmpty()) System.out.println("등기부등본 결과 리스트 비었음");
        else System.out.println(list.get(0).getComment());

    }
}