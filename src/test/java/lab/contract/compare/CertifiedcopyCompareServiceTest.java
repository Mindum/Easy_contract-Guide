package lab.contract.compare;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.compare.CertifiedcopyCompareService;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedcopyContent;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.user.persistence.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CertifiedcopyCompareServiceTest {
    @Autowired
    CertifiedcopyCompareService certifiedcopyCompareService;
    @Autowired
    ContractRepository contractRepository;

    public Contract set() {
        User user = User.builder()
                .username("홍길동")
                .email("test@test.com")
                .password("1234")
                .privacy_agreement_yn("y")
                .build();
        Contract contract = Contract.builder()
                .user(user)
                .contract_name("untitled")
                .contract_text("계약서 본문")
                .build();
        Long contractId = contractRepository.save(contract).getId();
        return contract;
    }
    @Test
    public void 등기부등본_주소비교() {
        Contract contract = set();
        ContractContent contractContent = ContractContent.builder()
                .contract(contract)
                .address("서울특별시 송파구 신천동 29")
                .rental_part("롯데월드타워앤드롯데월드몰 제월드타워동 제 46층 제 4605호").build();
        contract.setContractContent(contractContent);
        Certifiedcopy certifiedcopy = Certifiedcopy.builder()
                .contract(contract)
                .build();
        contract.setCertifiedcopy(certifiedcopy);
        CertifiedcopyContent certifiedcopyContent = CertifiedcopyContent.builder()
                .certifiedcopy(certifiedcopy)
                .total_address("서울특별시 송파구 신천동 29 롯데월드타워앤드롯데월드몰 제월드타워동 제 46층 제 4605호")
                .street_address("서울특별시 송파구 올림픽로 300")
                .build();
        certifiedcopy.setCertifiedcopyContent(certifiedcopyContent);
        assertThat(certifiedcopyCompareService.compareAddressWithCertified(contract).getResult()).isEqualTo("normal");
    }

//    @Test
//    public void 건축물대장_주소비교() {
//        Contract contract = set();
//        ContractContent contractContent = ContractContent.builder()
//                .contract(contract)
//                .address("서울특별시 송파구 신천동 29")
//                .rental_part("롯데월드타워앤드롯데월드몰 제월드타워동 제 46층 제 4605호").build();
//        contract.setContractContent(contractContent);
//        BuildingRegister buildingRegister = BuildingRegister.builder()
//                .contract(contract)
//                .build();
//        contract.setBuilding_register(buildingRegister);
//        BuildingRegisterContent buildingRegisterContent = BuildingRegisterContent.builder()
//                .location("서울특별시 송파구 신천동")
//                .location_number("29")
//                .street_address("서울특별시 송파구 올림픽로 300(신천동)")
//                .title("롯데월드타워앤드롯데월드몰 월드타워동")
//                .ho_title("4605")
//                .build();
//        buildingRegister.setBuildingRegisterContent(buildingRegisterContent);
//        assertThat(compare.compareAddressWithBuiliding(contract).isResult()).isTrue();
//    }
    @Test
    public void 등기부등본_소유자비교() {
        Contract contract = set();
        ContractContent contractContent = ContractContent.builder()
                .contract(contract)
                .lessor_name("강인광")
                .lessor_resident_number("000000-1111111")
                .lessor_address("서울특별시 송파구 신천동")
                .build();
        contract.setContractContent(contractContent);
        Certifiedcopy certifiedcopy = Certifiedcopy.builder()
                .contract(contract)
                .build();
        contract.setCertifiedcopy(certifiedcopy);
        CertifiedcopyContent certifiedcopyContent = CertifiedcopyContent.builder()
                .certifiedcopy(certifiedcopy)
                .owner_part(1.0)
                .owner_name("강인광")
                .owner_resident_number("000000-1")
                .owner_address("서울특별시 송파구 신천동")
                .build();

        certifiedcopy.setCertifiedcopyContent(certifiedcopyContent);
        assertThat(certifiedcopyCompareService.compareOwnerWithCertified(contract).getResult()).isEqualTo("normal");
    }
    @Test
    public void 등기부등본_공유자비교() {
        Contract contract = set();
        ContractContent contractContent = ContractContent.builder()
                .contract(contract)
                .lessor_name("강인광")
                .lessor_resident_number("000000-1111111")
                .lessor_address("서울특별시 송파구 신천동")
                .build();
        contract.setContractContent(contractContent);
        Certifiedcopy certifiedcopy = Certifiedcopy.builder()
                .contract(contract)
                .build();
        contract.setCertifiedcopy(certifiedcopy);
        CertifiedcopyContent certifiedcopyContent = CertifiedcopyContent.builder()
                .certifiedcopy(certifiedcopy)
                .owner_part(0.5)
                .owner_name("강인광")
                .owner_resident_number("000000-1")
                .owner_address("서울특별시 송파구 신천동")
                .sharer_part(0.5)
                .sharer_name("광인강")
                .sharer_resident_number("000000-1")
                .sharer_address("서울특별시 광진구 어쩌구동")
                .build();

        certifiedcopy.setCertifiedcopyContent(certifiedcopyContent);
        assertThat(certifiedcopyCompareService.compareOwnerWithCertified(contract).getResult()).isEqualTo("normal");
    }
    @Test
    public void 건축물대장_소유자비교() {
        Contract contract = set();
        ContractContent contractContent = ContractContent.builder()
                .contract(contract)
                .lessor_name("강인광")
                .lessor_resident_number("000000-1111111")
                .lessor_address("서울특별시 송파구 신천동")
                .build();
        contract.setContractContent(contractContent);
        BuildingRegister buildingRegister = BuildingRegister.builder()
                .contract(contract)
                .build();
        contract.setBuilding_register(buildingRegister);
        BuildingRegisterContent buildingRegisterContent = BuildingRegisterContent.builder()
                .owner_part(1.0)
                .owner_name("강인광")
                .owner_resident_number("000000-1")
                .owner_address("서울특별시 송파구 신천동")
                .build();
        buildingRegister.setBuildingRegisterContent(buildingRegisterContent);
        assertThat(certifiedcopyCompareService.compareOwnerWithBuilding(contract)).isTrue();
    }
}