package lab.contract.compare;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.certifiedcopy_content.persistence.CertifiedcopyContent;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class Compare {
    /**
     * 계약서 - 등기부등본 주소 비교
     */
    public Result compareAddressWithCertified (Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        /**
         * 소재지 비교
         * 지번인 경우 - 등기부등본 전체지번에 포함여부 확인
         * 도로명인 경우 - 등기부등본 도로명과 일치하는지 확인
         */
        String contractAddress = contractContent.getAddress();
        String contractRentalPart = contractContent.getRental_part();
        String certifiedcopyTotalAddress = certifiedcopyContent.getTotal_address();
        String certifiedcopyStreetAddress = certifiedcopyContent.getStreet_address();

        String cleanedContractAddress = contractAddress.replace(" ","");
        String cleanedContractRentalPart = contractRentalPart.replace(" ","");
        String cleanedCertifiedCopyTotalAddress = certifiedcopyTotalAddress.replace(" ","");
        String cleanedCertifiedCopyStreetAddress = certifiedcopyStreetAddress.replace(" ","");

        Pattern pattern = Pattern.compile("(로|길)(\\d+)");
        Matcher addressMatcher = pattern.matcher(cleanedContractAddress);
        if (addressMatcher.find()) { // 계약서의 소재지가 도로명주소인 경우
            if (!cleanedContractAddress.equals(cleanedCertifiedCopyStreetAddress)){
                return new Result(false,contractAddress,certifiedcopyStreetAddress);
            }
        } else { //계약서의 소재지가 지번인 경우
            if (!cleanedCertifiedCopyTotalAddress.contains(cleanedContractAddress)) {
                return new Result(false,contractAddress,certifiedcopyTotalAddress);
            }
        }

        /**
         * 임대(임차)할부분 비교
         * 지번 + 상세주소 or 상세주소만 있는 경우 - 등기부등본에 포함여부 확인
         * 도로명 주소 + 상세주소 - 임대할 부분에 등기부 도로명 포함여부  -> 맞으면 뒤에 상세주소 전체지번에서 포함여부 확인
         */
        Matcher rentalPartMatcher = pattern.matcher(cleanedContractRentalPart);
        if (rentalPartMatcher.find()) { // 임대할부분이 도로명주소인 경우
            if (!cleanedContractRentalPart.contains(cleanedCertifiedCopyStreetAddress)){
                return new Result(false,contractRentalPart,certifiedcopyStreetAddress);
            }
            String detail = cleanedContractRentalPart.substring(rentalPartMatcher.end());
            if (!certifiedcopyTotalAddress.contains(detail)) { //상세주소는 전체지번에서 비교
                return new Result(false,contractRentalPart,certifiedcopyStreetAddress);
                //정확히는 도로명주소 + 상세주소를 만들어서 targetB에 넣어줘야 함
            }
        } else { //임대할부분이 지번인 경우
            if (!cleanedCertifiedCopyTotalAddress.contains(cleanedContractRentalPart)) {
                return new Result(false,contractRentalPart,certifiedcopyTotalAddress);
            }
        }
        return new Result(true);
    }
    /**
     * 계약서 - 건축물대장 주소 비교
     */
    public Result compareAddressWithBuiliding (Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();

        String contractAddress = contractContent.getAddress();
        String contractRentalPart = contractContent.getRental_part();
        String buildingRegisterAddress = buildingRegisterContent.getLocation() + buildingRegisterContent.getLocation_number();
        String buildingStreetAddress = buildingRegisterContent.getStreet_address();
        String buildingTitle = buildingRegisterContent.getTitle();
        String buildingHoTitle = buildingRegisterContent.getHo_title();
        buildingStreetAddress = buildingStreetAddress.substring(0,buildingStreetAddress.indexOf("(")).trim();

        String cleanedContractAddress = contractAddress.replace(" ","");
        String cleanedContractRentalPart = contractRentalPart.replace(" ","");
        String cleanedBuildingAddress = buildingRegisterAddress.replace(" ","");
        String cleanedBuildingStreetAddress = buildingStreetAddress.replace(" ","");
        /**
         * 소재지 비교
         * 도로명주소인 경우
         * 지번인 경우
         */
        Pattern pattern = Pattern.compile("(로|길)(\\d+)");
        Matcher addressMatcher = pattern.matcher(cleanedContractAddress);
        if (addressMatcher.find()) { // 소재지가 도로명주소인 경우
            if (!cleanedContractAddress.equals(cleanedBuildingStreetAddress)){
                return new Result(false,contractAddress,buildingStreetAddress);
            }
        } else { //소재지가 지번인 경우
            if (!cleanedContractAddress.equals(cleanedBuildingAddress)) {
                return new Result(false,contractAddress,buildingRegisterAddress);
            }
        }
        /**
         * 임대(임차)할부분 비교
         * 건축물대장의 명칭과 호명칭 포함여부확인
         */

        /**
         * 등기부등본은 제월드타워동
         *  건축물대장은 월드타워동으로 나오기 때문에
         *  건축물대장의 명칭을 " " 기준으로 분리하고 포함여부를 확인
         */
        String[] title = buildingTitle.split(" ");
        for (int i=0;i< title.length;i++) {
            if (!cleanedContractRentalPart.contains(title[i])) {
                return new Result(false,contractRentalPart,buildingTitle);
            }
        }
        if (!cleanedContractRentalPart.contains(buildingHoTitle)) {
            return new Result(false,contractRentalPart,buildingHoTitle);
        }
        return new Result(true);
    }

    /**
     * 계약서 - 등기부등본 소유자 비교
     */
    public boolean compareOwnerWithCertified(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        String ownerName = certifiedcopyContent.getOwner_name();
        String ownerResidentNumber = certifiedcopyContent.getOwner_resident_number();
        String ownerAddress = certifiedcopyContent.getOwner_address();
        Double ownerPart = certifiedcopyContent.getOwner_part();
        String sharerName = certifiedcopyContent.getSharer_name();
        String sharerResidentNumber = certifiedcopyContent.getSharer_resident_number();
        String sharerAddress = certifiedcopyContent.getSharer_address();
        Double sharerPart = certifiedcopyContent.getSharer_part();
        /**
         * 지분이 반반이면 공유자 중 한명과 일치하면 true
         * 지분이 반반이 아니면 큰 쪽과 같아야 함
         */
        if (sharerPart == null || ownerPart > sharerPart) {
            return withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress);
        } else if (ownerPart < sharerPart) {
            return withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress);
        } else {
            /**
             * 공유자와 지분이 반반일때
             *  + 다른 공유자와도 거래하라고 알려주기
             */
            if (!withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress)
                    && !withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress)) {
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
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();
        String ownerName = buildingRegisterContent.getOwner_name();
        String ownerResidentNumber = buildingRegisterContent.getOwner_resident_number();
        String ownerAddress = buildingRegisterContent.getOwner_address();
        Double ownerPart = buildingRegisterContent.getOwner_part();
        String sharerName = buildingRegisterContent.getSharer_name();
        String sharerResidentNumber = buildingRegisterContent.getSharer_resident_number();
        String sharerAddress = buildingRegisterContent.getSharer_address();
        Double sharerPart = buildingRegisterContent.getSharer_part();
        /**
         * 지분이 반반이면 공유자 중 한명과 일치하면 true
         * 지분이 반반이 아니면 큰 쪽과 같아야 함
         */
        if (sharerPart == null || ownerPart > sharerPart) {
            return withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress);
        } else if (ownerPart < sharerPart) {
            return withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress);
        } else {
            /**
             * 공유자와 지분이 반반일때
             *  + 다른 공유자와도 거래하라고 알려주기
             */
            if (!withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress)
                    && !withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 소유자와 비교
     */

    public boolean withOwner(ContractContent contractContent, String name, String residentNumber, String address) {
        if (!contractContent.getLessor_name().equals(name)) {
            log.info("소유자 성명 정보가 일치하지 않습니다.");
            return false;
        }
        if (!contractContent.getLessor_resident_number().contains(residentNumber.replace("*",""))) {
            log.info("소유자 주민등록번호가 일치하지 않습니다.");
            return false;
        }
        if (!contractContent.getLessor_address().equals(address)) {
            log.info("소유자 주소가 일치하지 않습니다.");
            return false;
        }
        return true;
    }
    @Getter @Setter
    public class Result {
        private boolean result;
        private String targetA;
        private String targetB;
        Result (boolean result,String targetA,String targetB) {
            this.result = result;
            this.targetA = targetA;
            this.targetB = targetB;
        }

        public Result(boolean result) {
            this.result = result;
        }
    }
}
