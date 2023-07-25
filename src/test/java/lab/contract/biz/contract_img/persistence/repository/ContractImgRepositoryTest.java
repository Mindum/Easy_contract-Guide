package lab.contract.biz.contract_img.persistence.repository;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractImgRepositoryTest {

    @Autowired
    ContractImgRepository contractImgRepository;

    @After
    public void cleanup() { contractImgRepository.deleteAll(); }

    @Test
    public void 계약서이미지_DB연동_테스트() {
        //given
        Long contract_id = 1L;
        Integer page = 1;
        String url = "url";

        //when
        contractImgRepository.save(ContractImg.builder()
                .contract_id(contract_id)
                .page(page)
                .url(url)
                .build());

        //then
        //List<ContractImg> savedContractImgs = contractImgRepository.findByContract_idAndPage(contract_id, page);
        //assertNotNull(savedContractImgs);

        //ContractImg savedContractImg = savedContractImgs.get(0);
        //assertEquals(contract_id, savedContractImg.getContract_id());
        //assertEquals(page, savedContractImg.getPage());
        //assertEquals(url, savedContractImg.getUrl());

    }
}