package lab.contract.compare;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedcopyContent;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class Compare {
    /**
     * 계약서 - 등기부등본 주소 비교
     */
    public boolean compareAddressWithCertified (Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();

        String contractAddress = contractContent.getAddress() +" "+ contractContent.getRental_part();
        String certifiedcopyAddress = certifiedcopyContent.getTotal_address();
        String contractAddressCompare = contractAddress.replace(" ","");
        String certifiedcopyAddressCompare = certifiedcopyAddress.replace(" ","");
        System.out.println("contractAddress = " + contractAddress);
        System.out.println("certifiedcopyAddress = " + certifiedcopyAddress);
        if (contractAddressCompare.equals(certifiedcopyAddressCompare)) return true;
        return false;
    }
    /**
     * 계약서 - 건축물대장 주소 비교
     */
    public boolean compareAddressWithBuiliding (Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();
        String contractAddress = contractContent.getAddress() + contractContent.getRental_part();
        String buildingRegisterAddress = buildingRegisterContent.getLocation() + buildingRegisterContent.getLocation_number();
        String contractAddressCompare = contractAddress.replace(" ","");
        String buildingRegisterAddressCompare = buildingRegisterAddress.replace(" ","");
        System.out.println("contractAddressCompare = " + contractAddressCompare);
        System.out.println("buildingRegisterAddressCompare = " + buildingRegisterAddressCompare);
        if (!contractAddressCompare.contains(buildingRegisterAddressCompare)) {
            log.info("소재지 정보가 다릅니다.");
            return false;
        }
        /**
         * 등기부등본은 제월드타워동
         *  건축물대장은 월드타워동으로 나오기 때문에
         *  건축물대장의 명칭을 " " 기준으로 분리하고 포함여부를 확인
         */
        String[] title = buildingRegisterContent.getTitle().split(" ");
        for (int i=0;i< title.length;i++) {
            if (!contractAddressCompare.contains(title[i])) {
                System.out.println("명칭 정보가 다릅니다.");
                return false;
            }
        }
        if (!contractAddressCompare.contains(buildingRegisterContent.getHo_title())) {
            System.out.println("호수 정보가 다릅니다.");
            return false;
        }
        return true;
    }
    /**
     * 계약서 - 등기부등본 소유자 비교
     */
    public boolean compareOwnerWithCertified(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        String contractLessorName = contractContent.getLessor_name();
        String contractLessorResidentNumber = contractContent.getLessor_resident_number();
        String contractLessorAddress = contractContent.getLessor_address();
        /**
         * 지분이 반반이면 공유자 중 한명과 일치하면 true
         * 지분이 반반이 아니면 큰 쪽과 같아야 함
         */
        if (certifiedcopyContent.getOwner_part()>certifiedcopyContent.getSharer_part()) {
            if (!contractLessorName.equals(certifiedcopyContent.getOwner_name())) {
                log.info("소유자 성명 정보가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorResidentNumber.contains(certifiedcopyContent.getOwner_resident_number().replace("*",""))) {
                log.info("소유자 주민등록번호가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorAddress.equals(certifiedcopyContent.getOwner_address())) {
                log.info("소유자 주소가 일치하지 않습니다.");
                return false;
            }
        } else if (certifiedcopyContent.getOwner_part() < certifiedcopyContent.getSharer_part()) {
            if (!contractLessorName.equals(certifiedcopyContent.getSharer_name())) {
                log.info("소유자 성명 정보가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorResidentNumber.contains(certifiedcopyContent.getSharer_resident_number().replace("*",""))) {
                log.info("소유자 주민등록번호가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorAddress.equals(certifiedcopyContent.getSharer_address())) {
                log.info("소유자 주소가 일치하지 않습니다.");
                return false;
            }
        } else {
            if (!contractLessorName.equals(certifiedcopyContent.getSharer_name()) &&
                    !!contractLessorName.equals(certifiedcopyContent.getOwner_name())) {
                log.info("소유자 성명 정보가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorResidentNumber.contains(certifiedcopyContent.getOwner_resident_number().replace("*","")) &&
                    !contractLessorResidentNumber.contains(certifiedcopyContent.getSharer_resident_number().replace("*",""))) {
                log.info("소유자 주민등록번호가 일치하지 않습니다.");
                return false;
            }
            if (!contractLessorAddress.equals(certifiedcopyContent.getOwner_address()) &&
                    !contractLessorAddress.equals(certifiedcopyContent.getSharer_address())) {
                log.info("소유자 주소가 일치하지 않습니다.");
                return false;
            }
        }

        return true;
    }
    /**
     * 계약서 - 건축물대장 소유자 비교
     */
    public boolean compareOwnerWithBuilding(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();

        String contractLessorName = contractContent.getLessor_name();
        String contractLessorResidentNumber = contractContent.getLessor_resident_number();
        String contractLessorAddress = contractContent.getLessor_address();
        /**
         * 지분이 반반이면 공유자 중 한명과 일치하면 true
         * 지분이 반반이 아니면 큰 쪽과 같아야 함
         */
        return true;

    }

}
