package lab.contract.biz.allcontract.contract_img.service;

import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.allcontract.contract_img.persistence.ContractImgRepository;
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.user.persistence.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContractImgServiceTest {

    @Autowired
    ContractImgService contractImgService;
    @Autowired
    ContractImgRepository contractImgRepository;
    @Autowired
    ContractService contractService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;

}