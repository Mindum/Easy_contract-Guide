package lab.contract.analysis_result.compare;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.persistence.ResultFieldRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContractCompareService {
    private final ResultFieldRepository resultFieldRepository;
    private final ContractRepository contractRepository;


    public void saveContractComment(Long contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (!contractOptional.isPresent()) {
            // contract 예외 처리
        }

        String type = "normal";

        Contract contract = contractOptional.get();
        AllResult allResult = contract.getAll_result();

        String floorAndHo = checkFloorAndHo(contract); // 1번
        ResultField resultField = ResultField.builder()
                .comment(floorAndHo)
                .type(type)
                .build();
        allResult.addResultField(resultField);
        resultFieldRepository.save(resultField);

        String purpose = checkPurpose(contract); // 2번
        ResultField resultField2 = ResultField.builder()
                .comment(purpose)
                .type(type)
                .build();
        allResult.addResultField(resultField2);
        resultFieldRepository.save(resultField2);

        String lessor = checkLessor(contract); // 3번
        ResultField resultField3 = ResultField.builder()
                .comment(lessor)
                .type(type)
                .build();
        allResult.addResultField(resultField3);
        resultFieldRepository.save(resultField3);


        String repair = checkRepair(contract);  // 4번
        ResultField resultField4 = ResultField.builder()
                .comment(repair)
                .type(type)
                .build();
        allResult.addResultField(resultField4);
        resultFieldRepository.save(resultField4);

    }

    public String checkFloorAndHo(Contract contract) {
        ContractContent contractContent = contract.getContract_content();

        String rentalPart = contractContent.getRental_part();

        String[] parts = rentalPart.split(" ");
        String check = parts[parts.length - 1];
        String check1 = check.substring(check.length() - 1);
        System.out.println(check);

        StringBuilder result = new StringBuilder();
        result.append(rentalPart + "\n");

        if (check1.contains("층") || check1.contains("호")) {
            result.append("임대할 부분에 '층'이나 '호'라는 단어가 있습니다.\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n" +
                    "건축물대장의 명칭, 호명칭, 대지위치, 지번, 도로명주소가 계약서에 작성한 주소와 빠짐없이 같은지 확인해보세요.\n\n");
        } else {
            result.append("층과 호수가 명확히 기입되어있지 않을 경우 대항력이 상실 될 수 있습니다.\n" +
                    "이 경우 보증금을 돌려받지 못하는 상황이 발생 할 수 있습니다.\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n" +
                    "건축물대장의 명칭, 호명칭, 대지위치, 지번, 도로명주소가 계약서에 작성한 주소와 빠짐없이 같은지 확인해보세요.\n\n");
        }
        return(result.toString());
    }

    public String checkLessor(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        StringBuilder result = new StringBuilder();
        result.append(contractContent.getLessor_name()).append("\n");
        result.append(contractContent.getLessor_resident_number()).append("\n");
        result.append(contractContent.getLessor_address()).append("\n");
        result.append("계약서의 임대인이 등기부등본과 건축물대장의 소유자 정보와 빠짐없이 같은지 확인해보세요.\n");
        result.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.");

        return result.toString();
    }

    public String checkPurpose(Contract contract){
        ContractContent contractContent = contract.getContract_content();
        StringBuilder result = new StringBuilder();
        result.append(contractContent.getPurpose()).append("\n");
        result.append(contractContent.getDeposit()).append("\n");
        result.append("등기부등본 을구에 '채권최고액'이 설정되어있을 경우 (설정되어 있나요?)\n" +
                "'융자 + 보증금'이 집 시세의 70%를 넘어갈 경우 보증금을 돌려받지 못할 가능성이 있습니다.\n" +
                "주의하세요.");
        return result.toString();

    }

    public String checkRepair(Contract contract) {
        ContractContent contractContent = contract.getContract_content();

        String specialOption = contractContent.getSpecial_option();

        StringBuilder result = new StringBuilder();
        result.append(specialOption + "\n\n");

        if (specialOption.contains("수리")) {
            result.append("올려주신 계약서 내의 특약사항에 '수리'라는 단어가 포함되어있습니다.\n" +
                    "입주 전에 발생한 수리비와 관리비는 임대인이 부담하는 것이 보편적입니다.\n" +
                    "입주 전,후에 발생할 수 있는 비용에 대한 내용을 확인 후 임대인과 협의하세요.\n\n");
        } else {
            result.append("'수리'에 대한 특약사항이 없습니다.\n" +
                    "수리에 대한 내용을 임대인과 협의하세요.\n\n");
        }
        return result.toString();
    }
}