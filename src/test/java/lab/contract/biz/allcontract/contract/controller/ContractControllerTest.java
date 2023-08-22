package lab.contract.biz.allcontract.contract.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lab.contract.allcontract.contract.controller.ContractController;
import lab.contract.user.persistence.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    private MockMvc mockMvc;
    @Mock
    private User user;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
    @Test
    public void pdf파일_업로드() throws Exception {
        //given
        user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();

        /*String path = "C:/Users/2rhdw/OneDrive/문서/한이음/표준임대차계약서.pdf";
        MockMultipartFile file = getMockMultipartFile("표준임대차계약서","pdf",path);
        UploadRequestDto dto = new UploadRequestDto();
        dto.setUserId(user.getId());
        dto.setPdfFile(file);
        //when
*/
        MockMultipartFile file = new MockMultipartFile("표준임대차계약서","표준임대차계약서.png",MediaType.APPLICATION_PDF_VALUE,"표준임대차계약서".getBytes());
        //then


    }
    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName,fileName + "." +contentType,contentType,fileInputStream);
    }
}