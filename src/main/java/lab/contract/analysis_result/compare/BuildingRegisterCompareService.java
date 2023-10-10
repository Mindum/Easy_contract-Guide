package lab.contract.analysis_result.compare;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result_field.persistence.BuildingRegisterResultField;
import lab.contract.analysis_result.result_field.persistence.BuildingRegisterResultFieldRepository;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BuildingRegisterCompareService {
    static String NORMAL = "normal";
    static String STRONG = "strong";
    static int RATE = 100;
    private final ContractRepository contractRepository;
    private final BuildingRegisterResultFieldRepository buildingRegisterResultFieldRepository;


    public void saveBuildingRegisterResult(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        AllResult allResult = contract.getAll_result();

        BuildingRegisterCompareService.Result result1 = compareAddress(contract); // 1번
        BuildingRegisterResultField resultField = BuildingRegisterResultField.builder()
                .title(result1.title)
                .comment(result1.getComment())
                .type(result1.getResult())
                .allResult(allResult)
                .build();
        allResult.addBuildingRegisterResult(resultField);
        buildingRegisterResultFieldRepository.save(resultField);

        BuildingRegisterCompareService.Result result2 = compareRentalPart(contract); // 2번
        BuildingRegisterResultField resultField2 = BuildingRegisterResultField.builder()
                .title(result2.title)
                .comment(result2.getComment())
                .type(result2.getResult())
                .allResult(allResult)
                .build();
        allResult.addBuildingRegisterResult(resultField2);
        buildingRegisterResultFieldRepository.save(resultField2);

        BuildingRegisterCompareService.Result result3 = checkLessor(contract); // 3번
        BuildingRegisterResultField resultField3 = BuildingRegisterResultField.builder()
                .title(result3.title)
                .comment(result3.getComment())
                .type(result3.getResult())
                .allResult(allResult)
                .build();
        allResult.addBuildingRegisterResult(resultField3);
        buildingRegisterResultFieldRepository.save(resultField3);

        int originalRate = allResult.getRate();
        System.out.println("originalRate = " + originalRate);
        System.out.println(contract.getBuilding_register());
        if (contract.getBuilding_register()==null) {
            System.out.println("(originalRate + rate)/2 = " + (originalRate + RATE)/2);
            allResult.setRate((originalRate + RATE)/2);
        }else {
            System.out.println("(originalRate*2 + rate)/3 = "+(originalRate*2 + RATE)/3);
            allResult.setRate((originalRate*2 + RATE)/3);
        }
        RATE=100;
    }

    /**
     * 소재지 비교
     */
    public Result compareAddress(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();

        String contractAddress = contractContent.getAddress();
        //소재지
        String buildingRegisterAddress = buildingRegisterContent.getLocation() + buildingRegisterContent.getLocation_number();
        String buildingStreetAddress = buildingRegisterContent.getStreet_address();
        String extractbuildingStreetAddress = extractAddress(buildingStreetAddress);

        String cleanContractAddress = contractAddress.replaceAll("\\s", "");
        String cleanBuildingRegisterAddress = buildingRegisterAddress.replaceAll("\\s", "");
        String cleanBuildingStreetAddress = extractbuildingStreetAddress.replaceAll("\\s", "");

        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        result.setTitle("<b>소재지 분석 결과</b>\n\n");
        comment.append("소재지 : " + contractAddress + "\n");

        // "로" 또는 "길" 다음에 숫자가 모두 있는지 확인하는 정규 표현식 패턴
        Pattern pattern = Pattern.compile("(로|길)(\\d+)");
        // 패턴을 사용하여 문자열에서 일치하는 부분 찾기
        Matcher addressMatcher = pattern.matcher(cleanContractAddress);
        if (addressMatcher.find()) {
            comment.append("건축물대장 도로명주소 : " + extractbuildingStreetAddress + "\n\n");
            // 계약서 소재지 도로명주소일 때
            // 건축물대장 도로명주소와 비교
            if (cleanContractAddress.contains(cleanBuildingStreetAddress)) {
                result.setResult(NORMAL);
                //rate(NORMAL);
                RATE = 100;
                comment.append("건축물대장과 계약서의 소재지(도로명주소)가 일치합니다.\n\n");
            } else {
                result.setResult(STRONG);
                //rate(STRONG);
                RATE -= 33;
                comment.append("<span>건축물대장과 계약서의 소재지 주소(도로명주소)가 일치하지 않습니다.</span>\n\n" +
                        "계약서에 작성한 정보가 건축물대장과 다를 시 임차인이 저곳에 산다는 권리를 인정받기 어려워 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n" +
                        "임대차계약서를 다시 작성해주세요.\n\n\n");
            }
        } else {
            comment.append("건축물대장 대지위치와 지번 : " + buildingRegisterAddress + "\n\n");
            // 계약서의 소재지가 지번일 때
            // 건축물대장 대지위치 + 지번과 비교
            if (cleanContractAddress.contains(cleanBuildingRegisterAddress)) {
                comment.append("건축물대장과 계약서의 소재지(지번)가 일치합니다.\n\n");
                result.setResult(NORMAL);
                //rate(NORMAL);
                RATE = 100;
            } else {
                result.setResult(STRONG);
                //rate(STRONG);
                RATE -= 33;
                comment.append("<span>건축물대장과 계약서의 소재지 주소(지번)가 일치하지 않습니다.</span>\n\n" +
                        "계약서에 작성한 정보가 건축물대장과 다를 시 임차인이 저곳에 산다는 권리를 인정받기 어려워 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n" +
                        "임대차계약서를 다시 작성해주세요.\n\n\n");
            }
        }
        result.setComment(comment.toString());
        return result;
    }

    /**
     * 임대할 부분 비교
     */
    public Result compareRentalPart(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();

        String contractAddress = contractContent.getAddress();
        String contractRentalPart = contractContent.getRental_part();
        //소재지
        String buildingRegisterAddress = buildingRegisterContent.getLocation() + buildingRegisterContent.getLocation_number();
        String buildingStreetAddress = buildingRegisterContent.getStreet_address();
        String extractbuildingStreetAddress = extractAddress(buildingStreetAddress);

        String buildingTitle = buildingRegisterContent.getTitle();
        String buildingHoTitle = buildingRegisterContent.getHo_title();

        String cleanContractAddress = contractAddress.replaceAll("\\s", "");
        String cleanContractRentalPart = contractRentalPart.replaceAll("\\s", "");
        String cleanBuildingRegisterAddress = buildingRegisterAddress.replaceAll("\\s", "");
        String cleanBuildingStreetAddress = extractbuildingStreetAddress.replaceAll("\\s", "");

        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        result.setTitle("<b>임대할부분 분석 결과</b>\n\n");
        comment.append("임대할부분 : " + contractRentalPart + "\n\n");

        // "로" 또는 "길" 다음에 숫자가 모두 있는지 확인하는 정규 표현식 패턴
        Pattern pattern = Pattern.compile("(로|길)(\\d+)");
        Matcher rentalPartMatcher = pattern.matcher(cleanContractRentalPart);
        if(rentalPartMatcher.find()) {

            // 계약서 임대할부분 도로명주소일 때
            // 건축물대장 도로명주소와 비교
            if (cleanContractRentalPart.contains(cleanBuildingStreetAddress)) {
                comment.append("건축물대장과 계약서의 임대할 부분(도로명주소)이 일치합니다.\n\n");
                result.setResult(NORMAL);
                //rate(NORMAL);
                RATE -= 0;
            } else {
                result.setResult(STRONG);
                //rate(STRONG);
                RATE -= 8;
                comment.append("<span>건축물대장과 계약서의 임대할 부분이 일치하지 않습니다.</span>\n\n" +
                        "계약서에 작성한 정보가 건축물대장과 다를 시 임차인이 저곳에 산다는 권리를 인정받기 어려워 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n" +
                        "임대차계약서를 다시 작성해주세요.\n\n\n");
            }
        } else {
            // 계약서의 임대할부분이 지번일 때
            // 건축물대장 대지위치 + 지번과 비교
            if (cleanContractRentalPart.contains(cleanBuildingRegisterAddress)) {
                result.setResult(NORMAL);
                //rate(NORMAL);
                RATE -= 0;
                comment.append("건축물대장과 계약서의 임대할 부분(지번)이 일치합니다.\n\n" +
                        "임대하는 곳이 토지인가요?\n\n" +
                        "토지가 아니면 일반적으로 도로명주소로 적는 것이 적합합니다.\n\n\n");
            } else {
                result.setResult(STRONG);
                //rate(STRONG);
                RATE -= 8;
                comment.append("<span>건축물대장과 계약서의 임대할 부분이 일치하지 않습니다.</span>\n\n" +
                        "임대하는 곳이 토지인가요?\n\n" +
                        "토지가 아니면 일반적으로 도로명주소로 적는 것이 적합합니다.\n\n" +
                        "또한, 계약서에 작성한 정보가 건축물대장과 다를 시 임차인이 저곳에 산다는 권리를 인정받기 어려워 계약의 법적 유효성에 대한 의문이 제기될 수 있습니다.\n\n" +
                        "임대차계약서를 다시 작성해주세요.\n\n\n");
            }
        }

        /**
         * 층, 호 확인
         */
        String[] parts = contractRentalPart.split(" ");
        String check = parts[parts.length - 1];
        String check1 = check.substring(check.length() - 1);
        System.out.println(check);

        if (check1.contains("층") || check1.contains("호")) {
            result.setResult(NORMAL);
            //rate(NORMAL);
            RATE -= 0;
            comment.append("임대할 부분에 '층'이나 '호'라는 단어가 있습니다.\n\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n\n\n");
        } else {
            result.setResult(STRONG);
            //rate(STRONG);
            RATE -= 8;
            comment.append("<span>층과 호수가 명확히 기입되어있지 않을 경우 대항력이 상실 될 수 있습니다.</span>\n\n" +
                    "이 경우 보증금을 돌려받지 못하는 상황이 발생 할 수 있습니다.\n\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n\n\n");
        }

        /**
         * 건축물대장 명칭, 호명칭 비교
         */
        String title[] = buildingTitle.split(" ");
        boolean containsCleanContractRentalPart = false;

        for (int i = 0; i < title.length; i++) {
            if (cleanContractRentalPart.contains(title[i])) {
                containsCleanContractRentalPart = true;
                RATE -= 0;
            } else {
                RATE -= 4;
            }
        }

        // 반복문이 끝난 후 comment를 설정
        if (containsCleanContractRentalPart) {
            result.setResult(NORMAL);
            comment.append("건축물대장의 명칭이 임대할 부분에 포함되어있습니다.\n\n");
        } else {
            result.setResult(STRONG);
            comment.append("<span>건축물대장의 명칭이 임대할 부분에 포함되어있지 않습니다.</span>\n\n" +
                    "상세주소가 있는 경우 동, 층, 호를 명확하게 기재해야 합니다.\n\n\n");
        }

        if(cleanContractRentalPart.contains(buildingHoTitle)) {
            result.setResult(NORMAL);
            //rate(NORMAL);
            RATE -= 0;
            comment.append("건축물대장의 호명칭이 임대할 부분에 포함되어있습니다.\n\n");
        } else {
            result.setResult(STRONG);
            //rate(STRONG);
            RATE -= 8;
            comment.append("<span>건축물대장의 호명칭이 임대할 부분에 포함되어있지 않습니다.</span>\n\n" +
                    "상세주소가 있는 경우 동, 층, 호를 명확하게 기재해야 합니다.\n\n\n");
        }

        result.setComment(comment.toString());
        return result;
    }

    public Result checkLessor(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        BuildingRegister buildingRegister = contract.getBuilding_register();
        BuildingRegisterContent buildingRegisterContent = buildingRegister.getBuildingRegisterContent();

        String contractName = contractContent.getLessor_name();
        String contractAddress = contractContent.getLessor_address();
        String contractResidentNumber = contractContent.getLessor_resident_number();

        String ownerName = buildingRegisterContent.getOwner_name();
        String ownerResidentNumber = buildingRegisterContent.getOwner_resident_number();
        String ownerAddress = buildingRegisterContent.getOwner_address();
        String extractOwnerAddress = extractAddress(ownerAddress);
        Double ownerPart = buildingRegisterContent.getOwner_part();
        String sharerName = buildingRegisterContent.getSharer_name();
        String sharerResidentNumber = buildingRegisterContent.getSharer_resident_number();
        String sharerAddress = buildingRegisterContent.getSharer_address();
        Double sharerPart = buildingRegisterContent.getSharer_part();

        //String cleanContract

        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        result.setTitle("<b>소유자 정보 분석 결과</b>\n\n");


        if(sharerPart == 0 || ownerPart > sharerPart){
            comment.append("임대인 성명 : " + contractName + "\n");
            comment.append("임대인 주소 : " + contractAddress + "\n");
            comment.append("임대인 주민등록번호 : " + contractResidentNumber + "\n\n");

            comment.append("건축물대장 소유자 성명 : " + ownerName + "\n");
            comment.append("건축물대장 소유자 주소 : " + ownerAddress + "\n");
            comment.append("건축물대장 소유자 주민등록번호 : " + ownerResidentNumber + "\n\n");
            if(contractName.equals(ownerName)) {
                result.setResult(NORMAL);
                //rate(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 이름이 일치합니다.\n\n");
            } else {
                result.setResult(STRONG);
                //rate(STRONG);
                RATE -= 12;
                comment.append( "소유권 지분이 낮은 사람과 계약서를 작성했을 경우 해당 임대차계약은 무효이며 우선변제권을 갖지 못합니다.\n\n" +
                        "소유권 지분이 높은 사람과 다시 계약서를 작성해주세요.\n\n" +
                        "보다 안전한 방법은 소유권 지분을 가진 모든 사람과 계약서를 작성해주세요.\n\n" +
                        "<span>계약서의 임대인과 건축물대장의 소유자 이름이 일치하지 않습니다.</span>\n\n\n");
            }
            if(contractAddress.equals(extractOwnerAddress)){
                result.setResult(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 주소가 일치합니다.\n\n");
            } else {
                result.setResult(NORMAL);
                RATE -= 12;
                comment.append("<span>계약서의 임대인과 건축물대장의 소유자 주소가 일치하지 않습니다.</span>\n\n");
            }
            if(contractResidentNumber.equals(ownerResidentNumber)) {
                result.setResult(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 주민등록번호가 일치합니다.\n\n");
                comment.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.\n\n\n");
            } else {
                result.setResult(STRONG);
                RATE -= 11;
                comment.append("<span>계약서의 임대인과 건축물대장의 소유자 주민등록번호가 일치하지 않습니다.</span>\n\n");
                comment.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.\n\n\n");
            }
        } else if(ownerPart == sharerPart) {
            comment.append("임대인 성명 : " + contractName + "\n");
            comment.append("임대인 주소 : " + contractAddress + "\n");
            comment.append("임대인 주민등록번호 : " + contractResidentNumber + "\n\n");

            comment.append("건축물대장 소유자 성명 : " + ownerName + "\n");
            comment.append("건축물대장 소유자 주소 : " + ownerAddress + "\n");
            comment.append("건축물대장 소유자 주민등록번호 : " + ownerResidentNumber + "\n\n");
            comment.append("건축물대장 공유자 성명 : " + sharerName + "\n");
            comment.append("건축물대장 공유자 주소 : " + sharerAddress + "\n");
            comment.append("건축물대장 공유자 주민등록번호 : " + sharerResidentNumber + "\n\n");
            comment.append("소유권 지분이 동일할 경우 다른 소유자와도 임대차계약서를 작성하는 것이 안전합니다.\n\n");
            if(contractName.equals(ownerName) || contractName.equals(sharerName)) {
                result.setResult(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 이름이 일치합니다.\n\n");
            } else {
                result.setResult(STRONG);
                RATE -= 12;
                comment.append("<span>계약서의 임대인과 건축물대장의 소유자 이름이 일치하지 않습니다.</span>\n\n");
            }
            if(contractAddress.equals(extractOwnerAddress) || contractAddress.equals(sharerAddress)){
                result.setResult(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 주소가 일치합니다.\n\n");
            } else {
                result.setResult(STRONG);
                RATE -= 12;
                comment.append("<span>계약서의 임대인과 건축물대장의 소유자 주소가 일치하지 않습니다.</span>\n\n");
            }
            if(contractResidentNumber.equals(ownerResidentNumber) || contractResidentNumber.equals(sharerResidentNumber)) {
                result.setResult(NORMAL);
                RATE -= 0;
                comment.append("계약서의 임대인과 건축물대장의 소유자 주민등록번호가 일치합니다.\n\n");
                comment.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.\n\n\n");
            } else {
                result.setResult(STRONG);
                RATE -= 11;
                comment.append("<span>계약서의 임대인과 건축물대장의 소유자 주민등록번호가 일치하지 않습니다.</span>\n\n");
                comment.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.\n\n\n");
            }
        }
        result.setComment(comment.toString());
        return result;
    }

    public static String extractAddress(String fullAddress) {
        int indexOfParenthesis = fullAddress.indexOf("("); // "(" 문자열의 인덱스 찾기
        if (indexOfParenthesis != -1) {
            return fullAddress.substring(0, indexOfParenthesis); // "(" 이전까지의 부분 추출
        } else {
            // 주소에 "(" 없으면 전체 주소 반환
            return fullAddress;
        }
    }

    @Getter
    @Setter
    public class Result {
        private String title;
        private String result;
        private String comment;
    }
}