<<<<<<<< HEAD:src/test/java/lab/contract/biz/allcontract/contract/service/ContractServiceTest.java
package lab.contract.biz.allcontract.contract.service;

import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.user.persistence.UserRepository;
========
package lab.contract.allcontract.contract.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
>>>>>>>> master:src/test/java/lab/contract/allcontract/contract/service/ContractServiceTest.java
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContractServiceTest {

    @Autowired
    ContractService contractService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;
/*
    @Test
    public void 계약서등록_테스트() {
        //given
        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        userRepository.save(user);
        //when
        Long contractId = contractService.saveContract(user.getId());
        Optional<Contract> contract = contractRepository.findById(contractId);
        //then
        Assertions.assertThat(contract.get().getUser().getUsername()).isEqualTo("홍길동");

    }
    */
}