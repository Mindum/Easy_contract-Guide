package lab.contract.biz.contract_img.persistence.repository;

import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import lab.contract.biz.allcontract.contract.persistence.repository.ContractRepository;
import lab.contract.biz.allcontract.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.allcontract.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.persistence.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContractImgRepositoryTest {

    @Autowired
    ContractImgRepository contractImgRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;



    @Test
    public void 계약서이미지_DB연동_테스트() {
        //given
        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        userRepository.save(user);
        Contract contract = Contract.builder()
                .user(user)
                .contract_name("테스트 계약서")
                .created_at(LocalDateTime.now())
                .build();
        contractRepository.save(contract);
        Integer page = 1;
        String url = "url";

        //when
        contractImgRepository.save(ContractImg.builder()
                .contract(contract)
                .page(page)
                .url(url)
                .build());
        List<ContractImg> contractImgs = contractImgRepository.findAll();

        //then
        ContractImg contractImg = contractImgs.get(0);
        assertThat(contractImg.getUrl()).isEqualTo(url);
    }

}