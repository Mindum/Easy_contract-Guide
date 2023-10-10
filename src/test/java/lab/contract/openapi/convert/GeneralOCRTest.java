package lab.contract.openapi.convert;

import lab.contract.openapi.clovaocr.GeneralOCR;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GeneralOCRTest {
    @Autowired
    GeneralOCR generalOCR;

    @Test
    public void OCR_API_테스트() {
        String res = generalOCR.ocrapi("c2a1ff43-29a6-47da-8333-0423d28616ad_표준임대차계약서.pdf.png");
        System.out.println(res);
    }
}