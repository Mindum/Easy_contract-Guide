package lab.contract.findout.contract_content.service;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lab.contract.findout.contract_content.persistence.ContractContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ContractContentService {
    private final ContractRepository contractRepository;
    private final ContractContentRepository contractContentRepository;

    public ContractContent saveContractContent(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(()-> new EntityNotFoundException("Contract_text를 찾을 수 없습니다."));

        String contractText = contract.getContract_text();

        String address = extractAddress(contractText);
        String purpose = extractPurpose(contractText);
        String rental_part = extractRentalPart(contractText);
        String deposit = extractDeposit(contractText);
        String special_option = extractSpecialOption(contractText);
        String lessor_address = extractLessorAddress(contractText);
        String lessor_resident_number = extractLessorResidentNumber(contractText);
        String lessor_name = extractLessorName(contractText);

        ContractContent contractContent = ContractContent.builder()
                .contract(contract)
                .address(address)
                .purpose(purpose)
                .rental_part(rental_part)
                .deposit(deposit)
                .special_option(special_option)
                .lessor_address(lessor_address)
                .lessor_resident_number(lessor_resident_number)
                .lessor_name(lessor_name)
                .build();

        return contractContentRepository.save(contractContent);
    }

    private String extractAddress(String contractText) {
        String startword = "소재지";
        String lastword = "동";

        int startIndex = contractText.indexOf(startword); // 시작 단어 찾기
        if (startIndex != -1) {
            int firstIndex = contractText.indexOf(lastword, startIndex); // 끝 단어 찾기
            if (firstIndex != -1) {
                int secondIndex = contractText.indexOf(lastword, firstIndex + lastword.length()); // 첫 번째 '동'에서부터 두 번째 '동' 찾기
                if (secondIndex != -1) {
                    String address = contractText.substring(startIndex + lastword.length(), secondIndex);  // 시작부터 끝 단어까지 추출. 끝 단어 포함
                    String Address = address.replace(startword, "").trim();
                    return Address;
                }
            }
        }
        return ""; // 매칭되지 않은 경우
    }

    private String extractPurpose(String contractText) {
        String startword = "용도";
        String lastword = "면 적";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String purpose = contractText.substring(startIndex + startword.length(), lastIndex); // 시작부터 끝 단어까지 추출. 끝 단어 미포함
                String Purpose = purpose.replace(startword,"").trim();
                return Purpose;
            }
        }
        return "";
    }

    private String extractRentalPart(String contractText) {
        String startword = "임대할부분";
        String lastword = "호";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String rentalPart = contractText.substring(startIndex, lastIndex + lastword.length());  // 시작부터 끝 단어까지 추출. 끝 단어 포함
                String RentalPart = rentalPart.replace(startword,"").trim();
                return RentalPart;
            }
        }
        return "";
    }

    private String extractDeposit(String contractText) {
        String startword = "보증금 금";
        String lastword = "정";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String deposit = contractText.substring(startIndex + startword.length(), lastIndex);
                String Deposit = deposit.replace(startword,"").trim();
                return Deposit;
            }
        }
        return "";
    }

    private String extractSpecialOption(String contractText) {
        String startword = "특약사항";
        String lastword = "본";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String specialOption = contractText.substring(startIndex + startword.length(), lastIndex);
                String SpecialOption = specialOption.replace(startword, "").trim();
                return SpecialOption;
            }
        }
        return "";
    }

    private String extractLessorAddress(String contractText) {
        String startword = "주 소";
        String lastword = "임대인";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String lessorAddress = contractText.substring(startIndex + startword.length(), lastIndex);
                String LessorAddress = lessorAddress.replace(startword, "").trim();
                return LessorAddress;
            }
        }
        return "";
    }

    private String extractLessorResidentNumber(String contractText) {
        String startword = "주민등록번호";
        String lastword = "전 화";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String lessorResidentNumber = contractText.substring(startIndex + startword.length(), lastIndex);
                String LessorResidentNumber = lessorResidentNumber.replace(startword, "").trim();
                return LessorResidentNumber;
            }
        }
        return "";
    }

    private String extractLessorName(String contractText) {
        String startword = "성 명";
        String lastword = "인 대리인";

        int startIndex = contractText.indexOf(startword);
        if (startIndex != -1) {
            int lastIndex = contractText.indexOf(lastword, startIndex);
            if (lastIndex != -1) {
                String lessorName = contractText.substring(startIndex + startword.length(), lastIndex);
                String LessorName = lessorName.replace(startword, "").trim();
                return LessorName;
            }
        }
        return "";
    }
}
