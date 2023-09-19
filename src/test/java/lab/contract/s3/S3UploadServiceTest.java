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
        MultipartFile multipartFile = contractImgService.transferMultipart("a1397f9c-a8f1-4784-8235-0e1f9001916f_표준임대차계약서.pdf-4.png");
        s3UploadService.putPngFile(multipartFile);
    }

}