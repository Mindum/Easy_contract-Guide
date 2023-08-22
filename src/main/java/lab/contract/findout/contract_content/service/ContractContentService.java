package lab.contract.findout.contract_content.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.findout.contract_content.persistence.ContractContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ContractContentService {
    private final ContractContentRepository contractContentRepository;

    public Long saveContractContent(Contract contract){
        String contractText = contract.getContract_text();
        String[] contractContent = findOutContent(contractText);
        ContractContent saveContractContent = ContractContent.builder()
                .contract(contract)
                .address(contractContent[0])
                .purpose(contractContent[1])
                .rental_part(contractContent[2])
                .deposit(contractContent[3])
                .special_option(contractContent[4])
                .lessor_address(contractContent[5])
                .lessor_resident_number(contractContent[6])
                .lessor_name(contractContent[7])
                .build();
        return  contractContentRepository.save(saveContractContent).getId();
    }

    public String[] findOutContent(String contractText) {
        String location = findAsNextWord(contractText,"소재지","토 지").trim();
        String purpose = findAsNextWord(contractText,"구조·용도","면 적").trim();
        String rentalPart = findAsEndWord(contractText,"임대할부분",'층').trim();
        String deposit = findSecond(contractText,"보증금","계약금").trim();
        String specialOption = findAsNextWord(contractText,"특약사항","본 계약을").trim();
        String AllLessorInfo = findAsNextWord(contractText,"주 소","대리인");
        String[] lessorInfo = splitLessor(AllLessorInfo);
        String[] content = {location, purpose,rentalPart,deposit,specialOption,lessorInfo[0],lessorInfo[1],lessorInfo[2]};
        return content;
    }
    public String findAsEndWord(String contractText,String findKey,char endWord) {
        if (!containCheck(contractText,findKey)) return "찾는 단어가 존재하지 않습니다.";
        int StartIndex = contractText.indexOf(findKey);
        int EndIndex;
        StringBuilder findValue = new StringBuilder();
        for (int i=StartIndex;;i++) {
            if (contractText.charAt(i)==endWord) {
                EndIndex=i;
                break;
            }
        }
        for (int i=StartIndex;i<=EndIndex;i++) {
            findValue.append(contractText.charAt(i));
        }
        return findValue.toString();
    }
    public String findAsNextWord(String contractText,String findKey,String nextWord) {
        if (!containCheck(contractText,findKey)) return "찾는 단어가 존재하지 않습니다.";
        int startIndex = contractText.indexOf(findKey);
        int endIndex = contractText.indexOf(nextWord,startIndex+1);
        StringBuilder findValue = new StringBuilder();

        for (int i=startIndex;i<endIndex;i++) {
            findValue.append(contractText.charAt(i));
        }
        return findValue.toString();
    }
    public String findSecond(String contractText,String findKey,String nextWord) {
        if (!containCheck(contractText,findKey)) return "찾는 단어가 존재하지 않습니다.";
        int firstIndex = contractText.indexOf(findKey);
        int startIndex = contractText.indexOf(findKey,firstIndex+1);
        int endIndex = contractText.indexOf(nextWord,startIndex);
        StringBuilder findValue = new StringBuilder();

        for (int i=startIndex;i<endIndex;i++) {
            findValue.append(contractText.charAt(i));
        }
        return findValue.toString();
    }
    public boolean containCheck(String contractText,String findKey) {
        if (contractText.contains(findKey)) return true;
        return false;
    }
    public String[] splitLessor(String allLessor) {
        int address = 4;
        int residentNumber = allLessor.indexOf("주민등록번호") + 7;
        int nameStart = allLessor.indexOf("성 명")+ 4;
        int nameEnd = allLessor.lastIndexOf("인");
        String[] split = new String[3];
        String lessorAddress = "";
        String lessorResidentNumber = "";
        String lessorName = "";


        for (int i = address;i<residentNumber-11;i++) {
            lessorAddress +=String.valueOf(allLessor.charAt(i));
        }
        for (int i = residentNumber;i<residentNumber+14;i++) {
            lessorResidentNumber +=String.valueOf(allLessor.charAt(i));
        }
        for (int i = nameStart;i<nameEnd;i++) {
            lessorName +=String.valueOf(allLessor.charAt(i));
        }
        split[0] = lessorAddress.trim();
        split[1] = lessorResidentNumber.trim();
        split[2] = lessorName.trim();
        return split;
    }
}
