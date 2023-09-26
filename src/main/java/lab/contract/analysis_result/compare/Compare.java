package lab.contract.analysis_result.compare;

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
    static String NORMAL = "normal";
    static String STRONG = "strong";
    /**
     * 계약서 - 등기부등본 주소 비교
     */
    public Result compareAddressWithCertified (Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        Result result = new Result();
        StringBuilder comment = new StringBuilder();
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
            comment.append("임대 계약서 : " + contractAddress +"\n"+
                    "등기부등본 : "+ certifiedcopyStreetAddress+"\n\n");
            if (!cleanedContractAddress.equals(cleanedCertifiedCopyStreetAddress)){
                result.setResult(STRONG);
                comment.append("임대 계약서의 소재지와 등기부등본의 주소가 일치하지 않습니다.\n" +
                        "임대 계약서에 기재된 주소가 부동산에 등록된 주소와 일치하지 않는 경우 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n");
            }else{
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 주소가 일치합니다.\n\n") ;
            }
        } else { //계약서의 소재지가 지번인 경우
            comment.append("임대 계약서 : " + contractAddress +"\n"+
                    "등기부등본 : "+ certifiedcopyTotalAddress+"\n\n");
            if (!cleanedCertifiedCopyTotalAddress.contains(cleanedContractAddress)) {
                result.setResult(STRONG);
                comment.append("임대 계약서의 소재지와 등기부등본의 주소가 일치하지 않습니다.\n");
            }else{
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 주소가 일치합니다.\n\n") ;
            }
        }

        /**
         * 임대(임차)할부분 비교
         * 지번 + 상세주소 or 상세주소만 있는 경우 - 등기부등본에 포함여부 확인
         * 도로명 주소 + 상세주소 - 임대할 부분에 등기부 도로명 포함여부  -> 맞으면 뒤에 상세주소 전체지번에서 포함여부 확인
         */
        Matcher rentalPartMatcher = pattern.matcher(cleanedContractRentalPart);
        if (rentalPartMatcher.find()) { // 임대할부분이 도로명주소인 경우
            comment.append("임대 계약서 : " + contractRentalPart +"\n"+
                    "등기부등본 : "+ certifiedcopyStreetAddress+"\n\n");
            if (!cleanedContractRentalPart.contains(cleanedCertifiedCopyStreetAddress)){
                result.setResult(STRONG);
                comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치하지 않습니다.\n");
            }else comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치합니다.\n\n");

            String detail = cleanedContractRentalPart.substring(rentalPartMatcher.end());
            if (!certifiedcopyTotalAddress.contains(detail)) { //상세주소는 전체지번에서 비교
                result.setResult(STRONG);
                comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치하지 않습니다.\n");
                //정확히는 도로명주소 + 상세주소를 만들어서 targetB에 넣어줘야 함
            }else comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치합니다.\n\n");
        } else { //임대할부분이 지번인 경우
            comment.append("임대 계약서 : " + contractRentalPart +"\n"+
                    "등기부등본 : "+ certifiedcopyTotalAddress+"\n\n");
            if (!cleanedCertifiedCopyTotalAddress.contains(cleanedContractRentalPart)) {
                result.setResult(STRONG);
                comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치하지 않습니다.\n");
            }else comment.append("임대 계약서의 임대할 부분과 등기부등본의 주소가 일치합니다.\n\n");
        }
        if (result.getResult()==STRONG) comment.append("임대 계약서에 기재된 주소가 부동산에 등록된 주소와 일치하지 않는 경우 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n");
        result.setComment(comment.toString());
        return result;
    }
    /**
     * 계약서 - 등기부등본 소유자 비교
     */
    public Result compareOwnerWithCertified(Contract contract) {
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

        Result result = new Result();
        StringBuilder comment = new StringBuilder();

        comment.append("임대인 이름: " +contractContent.getLessor_name()).append("\n");
        comment.append("임대인 주민번호: " +contractContent.getLessor_resident_number()).append("\n");
        comment.append("임대인 주소: " +contractContent.getLessor_address()).append("\n");
        /**
         * 지분이 반반이면 공유자 중 한명과 일치하면 true
         * 지분이 반반이 아니면 큰 쪽과 같아야 함
         */
        if (sharerPart == null || ownerPart > sharerPart) {
            comment.append("소유자 이름: " +ownerName).append("\n");
            comment.append("소유자 주민번호: " +ownerResidentNumber).append("\n");
            comment.append("소유자 주소: " +ownerAddress).append("\n");
            if (withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress)) {
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 소유자 정보가 일치합니다.").append("\n\n");
            } else result.setResult(STRONG);
        } else if (ownerPart < sharerPart) {
            comment.append("소유자 이름: " +sharerName).append("\n");
            comment.append("소유자 주민번호: " +sharerResidentNumber).append("\n");
            comment.append("소유자 주소: " +sharerAddress).append("\n");
            if(withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress)){
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 소유자 정보가 일치합니다.").append("\n\n");
            } else result.setResult(STRONG);
        } else {
            /**
             * 공유자와 지분이 반반일때
             *  + 다른 공유자와도 거래하라고 알려주기
             */
            boolean ownerCheck = withOwner(contractContent, ownerName,ownerResidentNumber,ownerAddress);
            boolean sharerCheck = withOwner(contractContent, sharerName,sharerResidentNumber,sharerAddress);
            if (ownerCheck) {
                comment.append("소유자 이름: " +ownerName).append("\n");
                comment.append("소유자 주민번호: " +ownerResidentNumber).append("\n");
                comment.append("소유자 주소: " +ownerAddress).append("\n");
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 소유자 정보가 일치합니다.").append("\n\n");
            } else if (sharerCheck) {
                comment.append("소유자 이름: " +sharerName).append("\n");
                comment.append("소유자 주민번호: " +sharerResidentNumber).append("\n");
                comment.append("소유자 주소: " +sharerAddress).append("\n");
                result.setResult(NORMAL);
                comment.append("임대 계약서와 등기부등본의 소유자 정보가 일치합니다.").append("\n\n");
            }else {
                comment.append("소유자 이름: " +ownerName).append("\n");
                comment.append("소유자 주민번호: " +ownerResidentNumber).append("\n");
                comment.append("소유자 주소: " +ownerAddress).append("\n");
                comment.append("공유자 이름: " +sharerName).append("\n");
                comment.append("공유자 주민번호: " +sharerResidentNumber).append("\n");
                comment.append("공유자 주소: " +sharerAddress).append("\n");
                result.setResult(STRONG);
                comment.append("임대 계약서와 등기부등본의 소유자 정보가 일치합니다.").append("\n\n");
            }
        }
        if (result.getResult()==STRONG) {
            comment.append("임대 계약서의 임대인이 등기부등본의 등록된 소유자가 아니거나 필요한 권한이 없는 경우에는 계약이 무효로 볼 수 있습니다.");
        }
        result.setComment(comment.toString());
        return result;
    }

    /**
     * 등기부등본 위험단어 포함 결과
     */
    public Result compareRegisterPurpose(Contract contract) {
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        String registerPurpose = certifiedcopyContent.getRegister_purpose();
        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        result.setResult(NORMAL);
        if (registerPurpose.contains("가등기")) {
            result.setResult(STRONG);
            comment.append("등기목적에 \'가등기\'라는 단어가 포함되어 있습니다.\n +" +
                    "가등기는 소유권 이전이 예정되어 있음을 의미합니다\n"+
                    "입주 당일 소유권을 이전하는 경우 세입자는 대항력이 다음날 생기므로 보증금을 돌려받지 못할 수 있습니다.\n");
        }
        if (registerPurpose.contains("신탁")){
            result.setResult(STRONG);
            comment.append("등기목적에 \'신탁\'라는 단어가 포함되어 있습니다.\n" +
                    "건물 담보 대출로 인해 소유권이 신탁회사에 있음을 의미합니다.\n" +
                    "집주인이 신탁회사의 동의없이 계약을 진향하는 경우 계약의 효력이 없어 보증금을 돌려받지 못할 수 있습니다.\n");
        }
        if (registerPurpose.contains("압류")){
            comment.append("등기목적에 \'압류\'라는 단어가 포함되어 있습니다.\n+" +
                    "압류는 채권자가 집주인에게 빌려준 돈을 되돌려받기 위해 법적인 절차를 진행중임을 의미합니다\n" +
                    "집주인이 돈을 못갚을 시 경매가 진행되어 보증금을 돌려받지 못할 수 있습니다.\n");
            result.setResult(STRONG);
        }
        if (registerPurpose.contains("경매개시결정")){
            result.setResult(STRONG);
            comment.append("등기목적에 \'경매개시결정\'라는 단어가 포함되어 있습니다.\n" +
                    "경매개시결정은 집주인이 빚을 갚지 못해 집이 경매에 넘어간 것을 의미합니다.\n" +
                    "경매가 진행되면 집이 매각되어 채권자에게 채무가 변제되고, 세입자의 보증금은 선순위의 저당권이나 전세권등의 밀려 돌려받지 못할 수 있습니다.\n");

        }
        if (result.getResult() == NORMAL) {
            comment.append("등기부등본에 \'가등기\' , \'신탁\', \'압류\', \'경매개시결정\'과 같은 위험단어가 포함되어있지 않습니다.");
        }
        result.setComment(comment.toString());
        return result;
    }

    /**
     * 등기부등본 채권최고액 이슈 결과
     */
    public Result compareMortgage(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Long deposit = Long.valueOf(contractContent.getDeposit());
        System.out.println("deposit = " + deposit);
        Certifiedcopy certifiedcopy = contract.getCertifiedcopy();
        CertifiedcopyContent certifiedcopyContent = certifiedcopy.getCertifiedcopyContent();
        Long mortgage = certifiedcopyContent.getMortgage();
        System.out.println("mortgage = " + mortgage);
        Result result = new Result();
        if (mortgage == null) {
            result.setResult(NORMAL);
            result.setComment("근저당권이 설정되어 있지 않습니다.\n");
        }else {
            result.setResult(STRONG);
            Long realMortgage = mortgage * 100/120;
            System.out.println("realMortgage = " + realMortgage);
            Long forCalculate = deposit + realMortgage;
            System.out.println("forCalculate = " + forCalculate);
            result.setComment("근저당권이 설정되어 있습니다.\n" +
                    "채권최고액과 보증금을 합친 금액인 "+ forCalculate + "원이 집 시세의 70%미만일 경우 안전합니다.\n" +
                    "금액이 70%를 이상일 경우 집이 경매로 넘어갈 경우 보증금을 돌려받지 못할 수 있습니다.\n");
        }
        return result;

    }

    /**
     * 계약서 - 건축물대장 주소 비교
     */
//    public Result compareAddressWithBuiliding (Contract contract) {
//        ContractContent contractContent = contract.getContract_content();
//        BuildingRegister buildingRegister = contract.getBuilding_register();
//        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();
//
//        String contractAddress = contractContent.getAddress();
//        String contractRentalPart = contractContent.getRental_part();
//        String buildingRegisterAddress = buildingRegisterContent.getLocation() + buildingRegisterContent.getLocation_number();
//        String buildingStreetAddress = buildingRegisterContent.getStreet_address();
//        String buildingTitle = buildingRegisterContent.getTitle();
//        String buildingHoTitle = buildingRegisterContent.getHo_title();
//        buildingStreetAddress = buildingStreetAddress.substring(0,buildingStreetAddress.indexOf("(")).trim();
//
//        String cleanedContractAddress = contractAddress.replace(" ","");
//        String cleanedContractRentalPart = contractRentalPart.replace(" ","");
//        String cleanedBuildingAddress = buildingRegisterAddress.replace(" ","");
//        String cleanedBuildingStreetAddress = buildingStreetAddress.replace(" ","");
//        /**
//         * 소재지 비교
//         * 도로명주소인 경우
//         * 지번인 경우
//         */
//        Pattern pattern = Pattern.compile("(로|길)(\\d+)");
//        Matcher addressMatcher = pattern.matcher(cleanedContractAddress);
//        if (addressMatcher.find()) { // 소재지가 도로명주소인 경우
//            if (!cleanedContractAddress.equals(cleanedBuildingStreetAddress)){
//                return new Result(false,contractAddress,buildingStreetAddress);
//            }
//        } else { //소재지가 지번인 경우
//            if (!cleanedContractAddress.equals(cleanedBuildingAddress)) {
//                return new Result(false,contractAddress,buildingRegisterAddress);
//            }
//        }
//        /**
//         * 임대(임차)할부분 비교
//         * 건축물대장의 명칭과 호명칭 포함여부확인
//         */
//
//        /**
//         * 등기부등본은 제월드타워동
//         *  건축물대장은 월드타워동으로 나오기 때문에
//         *  건축물대장의 명칭을 " " 기준으로 분리하고 포함여부를 확인
//         */
//        String[] title = buildingTitle.split(" ");
//        for (int i=0;i< title.length;i++) {
//            if (!cleanedContractRentalPart.contains(title[i])) {
//                return new Result(false,contractRentalPart,buildingTitle);
//            }
//        }
//        if (!cleanedContractRentalPart.contains(buildingHoTitle)) {
//            return new Result(false,contractRentalPart,buildingHoTitle);
//        }
//        return new Result(true);
//    }
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
    @Getter
    @Setter
    public class Result {
        private String result;
        private String comment;
    }
}
