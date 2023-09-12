package lab.contract.s3;

import lab.contract.allcontract.contract_img.service.ContractImgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class S3UploadServiceTest {
    @Autowired
    S3UploadService s3UploadService;
    @Autowired
    ContractImgService contractImgService;

    @Test
    public void S3업로드_테스트() throws IOException {
        MultipartFile multipartFile = contractImgService.transferMultipart("2e041110-5793-42a8-9e4f-d95cc56789cb_한남더힐 등기부등본.pdf.png");
        s3UploadService.putPngFile(multipartFile);
    }

}