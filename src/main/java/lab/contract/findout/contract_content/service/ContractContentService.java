package lab.contract.findout.contract_content.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.findout.contract_content.persistence.ContractContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class ContractContentService {
    private final ContractContentRepository contractContentRepository;
    private final ContractRepository contractRepository;
    static String Text;

    public Long saveContractContent(Long contractId){
        Contract contract = contractRepository.findById(contractId).orElseThrow(EntityNotFoundException::new);
        Text = contract.getContract_text();

        String address = extractAddress();
        String purpose = extractPurpose();
        String rentalPart = extractRentalPart();
        String deposit = extractDeposit();
        String specialOption = extractSpecialOption();
        String lessorAddress = extractLessorAddress();
        String lessorResidentNumber = extractLessorResidentNumber();
        String lessorName = extractLessorName();

        ContractContent saveContractContent = ContractContent.builder()
                .contract(contract)
                .address(address)
                .purpose(purpose)
                .rental_part(rentalPart)
                .deposit(deposit)
                .special_option(specialOption)
                .lessor_address(lessorAddress)
                .lessor_resident_number(lessorResidentNumber)
                .lessor_name(lessorName)
                .build();
        contract.setContractContent(saveContractContent);
        return  contractContentRepository.save(saveContractContent).getId();
    }

    private String extractAddress() {
        String address = findAsNextWord("소재지", "토 지").trim();
        if (address == "찾는 단어가 존재하지 않습니다.") address = findAsNextWord("소재지", "토지").trim();

        return address; // 매칭되지 않은 경우
    }

    private String extractPurpose() {
        String purpose = findAsNextWord("용도", "면 적").trim();
        if (purpose == "찾는 단어가 존재하지 않습니다.") purpose = findAsNextWord("구조·용도", "면적");
        return purpose;
    }
    private String extractRentalPart() {
        String rentalPart = findAsNextWord("임대할부분","면 적").trim();
        if (rentalPart == "찾는 단어가 존재하지 않습니다.") rentalPart = findAsNextWord("임차할부분", "면적").trim();

        return rentalPart;
    }
    private String extractDeposit() {
        String deposit = findAsEndWord("₩",")").trim();
        deposit = deposit.replace(",","");
        if (deposit.charAt(deposit.length()-1) == '원') deposit = deposit.replace("원","");

        return deposit;
    }

    private String extractSpecialOption() {
        String specialOption = findAsNextWord("특약사항","본 계약을").trim();
        if (specialOption.contains("]")) specialOption = specialOption.replace("]","");

        return specialOption;
    }

    private String extractLessorAddress() {
        String lessorAddress = findAsNextWord("주 소", "임대인");

        return lessorAddress;
    }
    private String extractLessorResidentNumber() {
        String lessorResidentNumber = findAsNextWord("주민등록번호", "전 화");
        if (lessorResidentNumber == "찾는 단어가 존재하지 않습니다.") lessorResidentNumber = findAsNextWord("주민등록번호", "전화").trim();

        return lessorResidentNumber;
    }

    private String extractLessorName() {
        String lessorName = findAsNextWord("성 명", "인 대리인");
        if (lessorName == "찾는 단어가 존재하지 않습니다.") lessorName = findAsNextWord("성명", "인 대리인").trim();
        if (lessorName.contains("날인")) lessorName = lessorName.replace(" 날인","");

        return lessorName;
    }
    //끝단어 미포함
    public String findAsEndWord(String findKey,String endWord) {
        if (!containCheck(Text,findKey)) return "찾는 단어가 존재하지 않습니다.";
        if (!containCheck(Text,endWord)) return "찾는 단어가 존재하지 않습니다.";
        int startIndex = Text.indexOf(findKey);
        int endIndex = Text.indexOf(endWord,startIndex+1);

        String findValue = Text.substring(startIndex+findKey.length(),endIndex);
        findValue = findValue.replace(findKey,"").trim();
        return findValue;
    }
    //끝단어 포함
    public String findAsEndWordContainEndWord(String findKey,String endWord) {
        if (!containCheck(Text,findKey)) return "찾는 단어가 존재하지 않습니다.";
        if (!containCheck(Text,endWord)) return "찾는 단어가 존재하지 않습니다.";
        int startIndex = Text.indexOf(findKey);
        int endIndex = Text.indexOf(endWord,startIndex+1);

        String findValue = Text.substring(startIndex+findKey.length(),endIndex+endWord.length());
        findValue = findValue.replace(findKey,"").trim();
        return findValue;
    }
    public String findAsNextWord(String findKey,String nextWord) {
        if (!containCheck(Text,findKey)) return "찾는 단어가 존재하지 않습니다.";
        if (!containCheck(Text,nextWord)) return "찾는 단어가 존재하지 않습니다.";

        int startIndex = Text.indexOf(findKey);
        int endIndex = Text.indexOf(nextWord,startIndex+1);

        String findValue = Text.substring(startIndex+findKey.length(),endIndex);
        findValue = findValue.replace(findKey,"").trim();
        return findValue;
    }
    public String findSecond(String findKey,String nextWord) {
        if (!containCheck(Text,findKey)) return "찾는 단어가 존재하지 않습니다.";
        if (!containCheck(Text,nextWord)) return "찾는 단어가 존재하지 않습니다.";

        int firstIndex = Text.indexOf(findKey);
        int startIndex = Text.indexOf(findKey,firstIndex+1);
        int endIndex = Text.indexOf(nextWord,startIndex);

        String findValue = Text.substring(startIndex+findKey.length(),endIndex);
        findValue = findValue.replace(findKey,"").trim();
        return findValue;
    }
    public boolean containCheck(String contractText,String findKey) {
        if (contractText.contains(findKey)) return true;
        return false;
    }

}
