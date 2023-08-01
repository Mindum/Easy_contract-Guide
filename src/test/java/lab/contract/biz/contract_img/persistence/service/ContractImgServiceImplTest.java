package lab.contract.biz.contract_img.persistence.service;


import static org.assertj.core.api.Assertions.assertThat;
import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.contract_img.persistence.service.ContractImgService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.ByteArrayOutputStream;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContractImgServiceTest {

    @Autowired
    ContractImgService contractImgService;

    @Autowired
    ContractImgRepository contractImgRepository;

    // 1. 테스트용 PDF 파일 데이터를 가져오는 메소드
    private byte[] getSamplePdfData() throws IOException {
        // TODO: 테스트용 PDF 파일 경로를 입력하세요.
        String pdfFilePath = "C:/sample.pdf";

        FileInputStream fis = new FileInputStream(pdfFilePath);
        return IOUtils.toByteArray(fis);
    }

    @Test
    public void pdf를_png로_변환하여_db에_저장_테스트() throws IOException, ExecutionException, InterruptedException {
        // 2. 테스트용 PDF 파일 데이터 가져오기
        byte[] pdfData = getSamplePdfData();

        // 3. 서비스 메소드 호출
        Long contractId = 1L;
        contractImgService.convertPdfToJpgAndSaveToDB(contractId, pdfData);

        // 4. 결과 검증
        List<ContractImg> imagesByContractId = contractImgService.getImagesByContractId(contractId);
        assertThat(imagesByContractId).hasSize(1);

        ContractImg savedContractImg = imagesByContractId.get(0);
        assertThat(savedContractImg.getId()).isNotNull();
        assertThat(savedContractImg.getContract_id()).isEqualTo(contractId);
        assertThat(savedContractImg.getPage()).isEqualTo(1); // 예시로 1 페이지만 저장했기 때문에 1로 검증
        assertThat(savedContractImg.getUrl()).isNotNull(); // URL이 비어있지 않은지 검증
    }
}