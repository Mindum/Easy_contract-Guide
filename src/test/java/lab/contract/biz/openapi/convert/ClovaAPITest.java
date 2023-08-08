package lab.contract.biz.openapi.convert;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClovaAPITest {
    @Autowired
    ClovaAPI clovaAPI;

    @Test
    public void OCR_API_테스트() {
        String res = clovaAPI.ocrapi("c2a1ff43-29a6-47da-8333-0423d28616ad_표준임대차계약서.pdf.png");
        //Assertions.assertThat(res).isEqualTo("왜");
        System.out.println(res);
    }
}