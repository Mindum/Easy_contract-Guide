package lab.contract.biz.contract_img.service;

import lab.contract.biz.contract.persistence.repository.ContractRepository;
import lab.contract.biz.contract.service.ContractService;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.user.persistence.repository.UserRepository;
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