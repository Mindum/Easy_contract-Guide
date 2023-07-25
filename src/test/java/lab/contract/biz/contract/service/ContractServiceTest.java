package lab.contract.biz.contract.service;

import lab.contract.biz.contract.persistence.entity.Contract;
import lab.contract.biz.contract.persistence.repository.ContractRepository;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.presentation.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

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
}