package lab.contract.biz.contract.persistence.repository;

import lab.contract.biz.contract.persistence.entity.Contract;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.persistence.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
<<<<<<< HEAD

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
=======
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
>>>>>>> master

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContractRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;

    @Test
<<<<<<< HEAD
    public void DB연동_테스트() {
=======
    public void 계약서_DB연동_테스트() {
>>>>>>> master
        //given
        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        userRepository.save(user);
<<<<<<< HEAD

        //when
        contractRepository.save(Contract.builder()
                .user(user)
                .contract_name("untitled")
                .build());
        List<Contract> contracts = contractRepository.findAll();

        //then
        Contract contract = contracts.get(0);
        assertThat(contract.getContract_name()).isEqualTo("untitled");

    }
}
=======
        //when
        contractRepository.save(Contract.builder()
                .user(user)
                .contract_name("테스트 계약서")
                .build());
        List<Contract> contracts = contractRepository.findAll();
        //then
        Contract contract = contracts.get(0);
        assertThat(contract.getContract_name()).isEqualTo("테스트 계약서");

    }

}
>>>>>>> master
