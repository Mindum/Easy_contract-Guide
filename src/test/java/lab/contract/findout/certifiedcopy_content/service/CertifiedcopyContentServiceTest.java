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

import java.util.Optional;

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

        String[][] certifiedcopyText = {{"전체 지번","서울특별시 용산구 한남동 810 한남더힐 제106동 제1층 제103호\n"},
                {"소재지번과 도로명 주소","소재지번,건물명칭 및 번호\n" +
                        "서울특별사 용산구 한남동\n" +
                        "810\n" +
                        "한남더힐 제106동\n" +
                        "서울특별시 용산구 한남동\n" +
                        "810\n" +
                        "한남더힐 제106동\n" +
                        "[도로명주소]\n" +
                        "서울특별시 용산구 독서당로\n" +
                        "111 여\n"},
                {"건물 주소" , ""},
                {"권리자 및 기타사항","제103호\n" +
                        ")\n" +
                        "등기원인 및 기타사항\n" +
                        "2011년1월17일 대지권\n" +
                        "2011년2월25일\n" +
                        "권리자 및 기타사항\n" +
                        "소유자 한스자람주식회사 110111-3355933\n" +
                        "서울특별시 강남구 역삼동 658-13\n" +
                        "소유자 주식회사무궁화신탁 110111-2867418\n" +
                        "서울특별시 강남구 대치동 946-1\n" +
                        "글라스타워빌딩 19층\n" +
                        "신탁원부 제2011-443호\n" +
                        "소유자 한스자람주식회사 110111-3355933\n" +
                        "서울특별시 용산구 독서당로\n" +
                        "111(한남동, 한남더힐고객지원센터)\n" +
                        "공유자\n" +
                        "지분 2분의 1\n" +
                        "윤순도 571030-*******\n" +
                        "서울특별시 용산구 독서당로 111,106동\n" +
                        "103호(한남동, 한남더힐)\n" +
                        "지분 2분의 1\n" +
                        "정규자 570215-*******\n" +
                        "서울특별시 용산구 독서당로 111,106동\n" +
                        "103호(한남동, 한남더힐)\n" +
                        "거래가액 금6,300,000,000원\n" +
                        "채권최고액 금5,280,000,000원"},
                {"등기목적","대지권종류\n" +
                        "1 소유권대지권\n" +
                        "갑 구 】\n" +
                        "등기목적\n" +
                        "소유권보존\n" +
                        "소유권이전\n" +
                        "신탁\n" +
                        "소유권이전\n" +
                        "2번 신탁등기말소\n" +
                        "소유권이전\n" +
                        "공유자전원지분전부\n" +
                        "이전\n"},
                {"건물 주소","서울특별시 용산구 한남동 810 한남더힐 제106동 제1층 제103호\n"},
                {"권리자 및 기타사항","사항 )\n" +
                        "백\n" +
                        "관할등기소 서울서부지방법원 등기국\n" +
                        "기록사항 없는 갑구, 을구는 '기록사항 없음' 으로 표시함.\n" +
                        "법적인 효력이 없습니다.\n"},
                {"등기목적","그어진 부분은 말소사항을\n" +
                        "컬러 또는 흑백으로 출력\n" +
                        "등기사항증명서는 열람용이므로"}};

        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        Contract contract = Contract.builder()
                .user(user)
                .contract_name("untitled")
                .build();
        Certifiedcopy certifiedcopy = Certifiedcopy.builder()
                .contract(contract)
                .build();

        //Long contentId = certifiedcopyContentService.saveCertifiedcopyContent(certifiedcopy,certifiedcopyText);
        //Optional<CertifiedcopyContent> content = certifiedcopyContentRepository.findById(contentId);
        String total_address;
        String street_address;
        String register_purpose;
        String owner_name;
        String owner_resident_number;
        String owner_address;
        String owner_part;
        String sharer_name;
        String sharer_resident_number;
        String sharer_address;
        String sharer_part;
        Long mortgage;


    }

}