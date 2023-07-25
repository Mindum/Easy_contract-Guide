package lab.contract.biz.contract.controller;

import lab.contract.biz.contract.service.ContractService;
import lab.contract.biz.user.persistence.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContractService contractService;

    @Mock
    private User user;


    @Test
    public void pdf파일_업로드() {
        //given
        user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        //when
        mockMvc.perform(MockMvcRequestBuilders
                .post("/fileupload")
        )
        //then

    }
}