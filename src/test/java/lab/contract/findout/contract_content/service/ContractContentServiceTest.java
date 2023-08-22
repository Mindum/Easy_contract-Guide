package lab.contract.findout.contract_content.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.findout.contract_content.persistence.ContractContentRepository;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ContractContentServiceTest {

    @Autowired
    ContractContentService contractContentService;
    @Autowired
    ContractContentRepository contractContentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;

    @Test
    public void 계약서_내용_저장() {
        //given
        String contractText = "부동산임대차계약서 □ 세세 □월세 임대인과 임차인 쌍방은 아래 표시 부동산에 관하여 다음 계약내용과 같이 임대차계약을 체결한다. 1. 부동산의 표시 소재지 경기도 안양시 만안구 안양동 708-113 안양대학교 엔동 토 지 지 목 교육연구시설 면 적 3000m2 건 물 구조·용도 다세대주택 면 적 893.92m2 임대할부분 경기도 안양시 만안구 삼덕로 37번길 22(안양동) 1층 면 적 893.92m2 2. 계약내용 제 1 조 (목적) 위 부동산의 임대차에 한하여 임대인과 임차인은 합의에 의하여 임차보증금 및 차임을 아래와 같이 지불하기로 한다. 보증금 금 일천오백만원정 (₩ 15,000,000원) 계약금 금 이백만원정 (₩ 2,000,000원) 계약시에 지불하고 영수함. 영수자 ( 김철수 인) 중도금 금 일백오십만원정은 2023년 5월 25일에 지불하며 잔 금 금 오십만원정(₩ 500,000원)은 2023년 8월 8일에 지불한다. 차 임 금 삼십만원정(₩ 300,000원)으로 매월 25일에 지불한다. 제 2조 (존속기간) 임대인은 위 부동산을 임대차 목적대로 사용·수익할 수 있는 상태로 2023년 8월 30일까지 임차인에게 인도하며, 임대차 기간은 인도일로부터 2023년 12월 30일까지로 한다.( 4개월) 제 3조 (용도변경 및 전대 등) 임차인은 임대인의 동의없이 위 부동산의 용도나 구조를 변경하거나 전대·임차권 양도 또는 담보제공을 하지 못하며 임대차 목적 이외의 용도로 사용할 수 없다. 제 4조 (계약의 해지) 임차인이 제3조를 위반하였을 때 임대인은 즉시 본 계약을 해지 할 수 있다. 제 5조 (계약의 종료) 임대차계약이 종료된 경우에 임차인은 위 부동산을 원상으로 회복하여 임대인에게 반환한다. 이러한 경우 임대인 은 보증금을 임차인에게 반환하고, 연체 임대료 또는 손해배상금이 있을 때는 이들을 제하고 그 잔액을 반환한다. 제 6조 (계약의 해제) 임차인이 임대인에게 중도금(중도금이 없을 때는 잔금)을 지불하기 전까지, 임대인은 계약금의 배액을 상환하고, 임차인은 계약금을 포기하고 본 계약을 해제할 수 있다. 제 7조 (채무불이행과 손해배상) 임대인 또는 임차인이 본 계약상의 내용에 대하여 불이행이 있을 경우 그 상대방은 불이행한 자에 대하 여 서면으로 최고하고 계약을 해제 할 수 있다. 그리고 계약 당사자는 계약해제에 따른 손해배상을 각각 상대방에 대하여 청구할 수 있으며, 손해배상에 대하여 별도의 약정이 없는 한 계약금을 손해배상의 기준으로 본다. 제 8조 (중개보수) 개업공인중개사는 임대인과 임차인이 본 계약을 불이행함으로 인한 책임을 지지 않는다. 또한, 중개보수는 본 계약체 결과 동시에 계약 당사자 쌍방이 각각 지불하며, 개업공인중개사의 고의나 과실없이 본 계약이 무효·취소 또는 해제되어도 중개보수는 지급한다. 공동중개인 경우에 임대인과 임차인은 자신이 중개 의뢰한 개업공인중개사에게 각각 중개보수를 지급한다.(중개보수는 거래 가액의 %로 한다.) 제 9 조 (중개대상물확인·설명서 교부 등) 개업공인중개사는 중개대상물 확인·설명서를 작성하고 업무보증관계증서(공제증서 등) 사본을 첨 부하여 계약체결과 동시에 거래당사자 쌍방에게 교부한다. 임대인은 임차인이 잔금을 지급하는 다음 날까지 해당 목적물에 대하여 근저당권 및 기타 제한물권 설정을 하지 않는다. 특약사항 이를 위반 시 계약은 즉시 무효가 되며 임대인은 임차인에게 위약금을 지불한다. 임차인이 목적물에 입주하기 전일까지의 공과금 및 관리비는 임대인이 정산한다. 임대인은 별도의 사전 협의가 없는 경우, 임대계약 만료시 새 임대차 여부와 관계없이 임대차보증금을 즉시 임차인에게 반환한다. 임대인은 임차인의 전세자금대출 및 전세보증보험가입을 위한 절차에 동의하고 협조하며, 대출상환 및 부대비용은 임차인이 부담한 다. 전세자금대출 심사과정에서 진행이 불가한 경우, 해당 임대차 계약은 무효로 하며 임대인은 임차인에게 계약금을 전액 반환하지 않 는다. 임차인이 입주하기 전에 발생한 임차 상가건물의 하자는 임차인이 직접 수리한다. 본 계약을 증명하기 위하여 계약 당사자가 이의 없음을 확인하고 각각 서명·날인 후 임대인, 임차인 및 개업공인중개사는 매장마다 간인 하여야 하며, 각각 1통씩 보관한다. 2023년 5월 20일 주 소 인천광역시 연수구 임대인 주민등록번호 000508-4034567 전 화 010-2922-9168 성 명 곽희선 인 대리인 주 소 주민등록번호 성명 주 소 경기도만안구 임차인 주민등록번호 980726-1234567 전 화 010-3131-7878 성 명 김철수 인 대리인 주소 주민등록번호 성 명 사무소소재지 경기도 만안구 안양 1번가 사무소소재지 개업공인중개사 사무소명칭 행복공인중개사 사무소명칭 대 표 서명및날인 방은지 인 대 표 서명및날인 인 등록번호 871-25-91674 전화 031-123-4567 등록번호 전화 소속공인중개사 서명 및 날인 방은지 인 소속공인중개사 서명 및 날인 인 ";
        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        userRepository.save(user);
        Contract contract = Contract.builder()
                .user(user)
                .contract_name("untitled")
                .contract_text(contractText)
                .build();
        contractRepository.save(contract);

        //when
        Long contentId = contractContentService.saveContractContent(contract);
        Optional<ContractContent> content = contractContentRepository.findById(contentId);
        //then
        String location = "경기도 안양시 만안구 안양동 708-113 안양대학교 엔동";
        String purpose = "다세대주택";
        String rentalPart ="경기도 안양시 만안구 삼덕로 37번길 22(안양동) 1층";
        String deposit = "일천오백만원정 (₩ 15,000,000원)";
        String specialOption ="이를 위반 시 계약은 즉시 무효가 되며 임대인은 임차인에게 위약금을 지불한다. 임차인이 목적물에 입주하기 전일까지의 공과금 및 관리비는 임대인이 정산한다. 임대인은 별도의 사전 협의가 없는 경우, 임대계약 만료시 새 임대차 여부와 관계없이 임대차보증금을 즉시 임차인에게 반환한다. 임대인은 임차인의 전세자금대출 및 전세보증보험가입을 위한 절차에 동의하고 협조하며, 대출상환 및 부대비용은 임차인이 부담한 다. 전세자금대출 심사과정에서 진행이 불가한 경우, 해당 임대차 계약은 무효로 하며 임대인은 임차인에게 계약금을 전액 반환하지 않 는다. 임차인이 입주하기 전에 발생한 임차 상가건물의 하자는 임차인이 직접 수리한다.";
        String lessorAddress = "인천광역시 연수구";
        String lessorNumber = "000508-4034567";
        String lessorName = "곽희선";

        assertThat(content.get().getAddress()).isEqualTo(location);
        assertThat(content.get().getPurpose()).isEqualTo(purpose);
        assertThat(content.get().getRental_part()).isEqualTo(rentalPart);
        assertThat(content.get().getDeposit()).isEqualTo(deposit);
        assertThat(content.get().getSpecial_option()).isEqualTo(specialOption);
        assertThat(content.get().getLessor_address()).isEqualTo(lessorAddress);
        assertThat(content.get().getLessor_resident_number()).isEqualTo(lessorNumber);
        assertThat(content.get().getLessor_name()).isEqualTo(lessorName);


    }
}