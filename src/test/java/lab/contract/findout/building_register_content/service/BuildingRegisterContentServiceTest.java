package lab.contract.findout.building_register_content.service;

import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class BuildingRegisterContentServiceTest {

    @Autowired
    private BuildingRegisterContentService buildingRegisterContentService;

    @Test
    public void testContentDivision() {
        String testText = "대지위치 인천광역시 미추홀구 용현동 지번\n" +
                "79-23 도로명주소 인천광역시 미추홀구 인하로91번길 94 (용현동) 성명(명칭)\n" +
                "주민(법인)등록번호\n" +
                "(부동산등기용등록번호)\n" +
                "민형근\n" +
                "860512-1\n" +
                "박희정\n" +
                "590312-2* 주소\n" +
                "인천광역시 남구 낙섬중로77번\n" +
                "길5,601호 (용현동,서정원룸\n" +
                ")\n" +
                "인천광역시 남구 낙섬중로77번\n" +
                "길 5,601호 (용현동,서정원룸\n" +
                ") 소유권\n" +
                "지분\n" +
                "9/10\n" +
                "1/10";

        BuildingRegisterContent result = buildingRegisterContentService.contentDivision(testText);

        assertEquals("인천광역시 미추홀구 용현동", result.getLocation());
        assertEquals("79-23", result.getLocation_number());
        assertEquals("인천광역시 미추홀구 인하로91번길 94 (용현동)", result.getStreet_address());
        assertEquals("민형근", result.getOwner_name());
        assertEquals("860512-1", result.getOwner_resident_number());

        // 이하 생략
    }
}