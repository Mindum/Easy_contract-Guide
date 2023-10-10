package lab.contract.findout.certifiedcopy_content.service;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcertified.certifiedcopy.persistence.CertifiedcopyRepository;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedcopyContent;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedcopyContentRepository;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CertifiedcopyContentServiceTest {
    @Autowired
    CertifiedcopyContentService certifiedcopyContentService;
    @Autowired
    CertifiedcopyContentRepository certifiedcopyContentRepository;

    @Test
    public void 등기부등본_내용_저장() {

        String[][] a = {{"전체 지번","서울특별시 송파구 신천동 29 롯데월드타워앤드롯데월드몰 제월드타워동 제46층 제4605호\n"},
                {"소재지번과 도로명주소","소재지번,건물명칭 및번호\n"+
                        "서울특별시 송파구 신천동\n"+
                        "29\n"+
                        "롯데월드타워앤드롯데월드몰\n"+
                        "제월드타워동\n"+
                                "[도로명주소]\n"+
                        "서울특별시 송파구 올림픽로\n"+
                        "300 여\n"+
                        "열\n"},
                {"전체 지번","서울특별시 송파구 신천동 29 롯데월드타워앤드롯데월드몰 제월드타워동 제46층 제4605호\n"},
        {"권리자 및 기타사항","권리자 및 기타사항\n"+
        "소유자 롯데물산주식회사 110111-0320707\n"+
        "서울특별시 송파구 올림픽로 300(신천동)\n"+
        "소유자 타이완인 강인광 730215-***\n"+
        "서울특별시 송파구 가락로 148(송파동)\n"+
        "거래가액 금4,980,600,000원\n"+
        "관한 사항 )\n"+
        "권리자 및 기타사항\n"+
        "채권최고액 금5,280,000,000원\n"+
        "채무자 타이완인강인광\n"+
        "서울특별시 송파구 가락로 148(송파동)\n"+
        "근저당권자 주식회사하나은행 110111-0672538\n"+
        "서울특별시 중구 을지로 35 (을지로1가)\n"+
        "( 롯데월드타워금융센터 )\n"+
        "용 관할등기소 서울동부지방법원 등기국\n"+
        "기록사항 없는 갑구, 을구는 '기록사항 없음' 으로 표시함.\n"+
        "법적인 효력이 없습니다.\n"},
        {"등기목적","등기목적\n"+
        "소유권보존\n"+
        "소유권이전\n"+
        "을 구 】\n"+
        "등기목적\n"+
        "근저당권설정\n"+
        "그어진 부분은 말소사항을\n"+
        "컬러 또는 흑백으로 출력\n"+
        "등기사항증명서는 열람용이므로\n"}};
        ArrayList<String[]> certifiedcopyText = new ArrayList<>();
        for (int i=0;i<a.length;i++) {
            certifiedcopyText.add(a[i]);
        }
        for (int i=0;i<certifiedcopyText.size();i++) {
            System.out.println(certifiedcopyText.get(i)[0] +" "+certifiedcopyText.get(i)[1]);
        }
        Long contentId = certifiedcopyContentService.saveCertifiedcopyContent(1L, certifiedcopyText);
        Optional<CertifiedcopyContent> content = certifiedcopyContentRepository.findById(contentId);
        String total_address = "서울특별시 송파구 신천동 29 롯데월드타워앤드롯데월드몰 제월드타워동 제46층 제4605호\n";
        String street_address = "서울특별시 송파구 올림픽로 300";
        String register_purpose = "등기목적\n" +
                "소유권보존\n" +
                "소유권이전\n" +
                "을 구 】\n" +
                "등기목적\n" +
                "근저당권설정\n" +
                "그어진 부분은 말소사항을\n" +
                "컬러 또는 흑백으로 출력\n" +
                "등기사항증명서는 열람용이므로\n";
        String owner_name = "타이완인";
        String owner_resident_number = "강인광 730215-***";
        String owner_address = "서울특별시 송파구 가락로 148(송파동)";
        Double owner_part = 1.0;
        String sharer_name;
        String sharer_resident_number;
        String sharer_address;
        String sharer_part;
        Long mortgage = 5280000000L;

        assertThat(content.get().getTotal_address()).isEqualTo(total_address);
        assertThat(content.get().getStreet_address()).isEqualTo(street_address);
        assertThat(content.get().getRegister_purpose()).isEqualTo(register_purpose);
        assertThat(content.get().getOwner_name()).isEqualTo(owner_name);
        assertThat(content.get().getOwner_resident_number()).isEqualTo(owner_resident_number);
        assertThat(content.get().getOwner_address()).isEqualTo(owner_address);
        assertThat(content.get().getOwner_part()).isEqualTo(owner_part);
        assertThat(content.get().getMortgage()).isEqualTo(mortgage);

    }

}