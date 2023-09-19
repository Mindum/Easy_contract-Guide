package lab.contract.analysis_result.compare;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.allcontract.contract.persistence.ContractRepository;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lab.contract.analysis_result.result_field.persistence.ResultFieldRepository;
import lab.contract.findout.contract_content.persistence.ContractContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    static String NORMAL = "normal";
    static String STRONG = "strong";


    public void saveContractComment(Long contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (!contractOptional.isPresent()) {
            // contract 예외 처리
        }

        Contract contract = contractOptional.get();
        AllResult allResult = contract.getAll_result();

        Result result1 = checkFloorAndHo(contract); // 1번
        ResultField resultField = ResultField.builder()
                .comment(result1.comment)
                .type(result1.result)
                .build();
        allResult.addResultField(resultField);
        resultFieldRepository.save(resultField);

        Result result2 = checkPurpose(contract); // 2번
        ResultField resultField2 = ResultField.builder()
                .comment(result2.comment)
                .type(result2.result)
                .build();
        allResult.addResultField(resultField2);
        resultFieldRepository.save(resultField2);

        Result result3 = checkLessor(contract); // 3번
        ResultField resultField3 = ResultField.builder()
                .comment(result3.comment)
                .type(result3.result)
                .build();
        allResult.addResultField(resultField3);
        resultFieldRepository.save(resultField3);


        Result result4 = checkRepair(contract);  // 4번
        ResultField resultField4 = ResultField.builder()
                .comment(result4.comment)
                .type(result4.result)
                .build();
        allResult.addResultField(resultField4);
        resultFieldRepository.save(resultField4);

    }

    public Result checkFloorAndHo(Contract contract) {
        ContractContent contractContent = contract.getContract_content();

        String rentalPart = contractContent.getRental_part();

        String[] parts = rentalPart.split(" ");
        String check = parts[parts.length - 1];
        String check1 = check.substring(check.length() - 1);
        System.out.println(check);

        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        comment.append("[주소 분석 결과]\n\n");
        comment.append(rentalPart + "\n");

        if (check1.contains("층") || check1.contains("호")) {
            result.setResult(NORMAL);
            comment.append("임대할 부분에 '층'이나 '호'라는 단어가 있습니다.\n\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n\n" +
                    "건축물대장의 명칭, 호명칭, 대지위치, 지번, 도로명주소가 계약서에 작성한 주소와 빠짐없이 같은지 확인해보세요.\n\n\n");
        } else {
            result.setResult(STRONG);
            comment.append("층과 호수가 명확히 기입되어있지 않을 경우 대항력이 상실 될 수 있습니다.\n\n" +
                    "이 경우 보증금을 돌려받지 못하는 상황이 발생 할 수 있습니다.\n\n" +
                    "등기부등본의 소재지번과 계약서에 작성한 주소가 빠짐없이 같은지 확인해보세요.\n\n" +
                    "건축물대장의 명칭, 호명칭, 대지위치, 지번, 도로명주소가 계약서에 작성한 주소와 빠짐없이 같은지 확인해보세요.\n\n\n");
        }
        result.setComment(comment.toString());
        return result;
    }

    public Result checkLessor(Contract contract) {
        ContractContent contractContent = contract.getContract_content();
        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        comment.append("[임대인 분석 결과]\n\n");
        comment.append(contractContent.getLessor_name()).append("\n\n");
        comment.append(contractContent.getLessor_resident_number()).append("\n\n");
        comment.append(contractContent.getLessor_address()).append("\n\n");
        comment.append("계약서의 임대인이 등기부등본과 건축물대장의 소유자 정보와 빠짐없이 같은지 확인해보세요.\n\n");
        comment.append("임대인의 주민등록증을 확인해보세요. 계약서 상의 임대인의 정보와 일치하는지 확인하세요.\n\n\n");
        result.setResult(NORMAL);
        result.setComment(comment.toString());
        return result;
    }

    public Result checkPurpose(Contract contract){
        ContractContent contractContent = contract.getContract_content();
        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        comment.append("[건물 용도에 따른 결과]\n\n");
        comment.append(contractContent.getPurpose()).append("\n\n");
        comment.append(contractContent.getDeposit()).append("\n\n");
        comment.append("등기부등본 을구에 '채권최고액'이 설정되어있을 경우\n\n" +
                "'융자 + 보증금'이 집 시세의 70%를 넘어갈 경우 보증금을 돌려받지 못할 가능성이 있습니다.\n\n" +
                "주의하세요.");
        result.setResult(NORMAL);
        result.setComment(comment.toString());
        return result;

    }

    public Result checkRepair(Contract contract) {
        ContractContent contractContent = contract.getContract_content();

        String specialOption = contractContent.getSpecial_option();

        Result result = new Result();
        StringBuilder comment = new StringBuilder();
        comment.append("[특약사항]\n\n");
        comment.append(specialOption + "\n\n");

        if (specialOption.contains("수리")) {
            result.setResult(NORMAL);
            comment.append("올려주신 계약서 내의 특약사항에 '수리'라는 단어가 포함되어있습니다.\n\n" +
                    "입주 전에 발생한 수리비와 관리비는 임대인이 부담하는 것이 보편적입니다.\n\n" +
                    "입주 전,후에 발생할 수 있는 비용에 대한 내용을 확인 후 임대인과 협의하세요.\n\n\n");
        } else {
            result.setResult(STRONG);
            comment.append("'수리'에 대한 특약사항이 없습니다.\n\n" +
                    "수리에 대한 내용을 임대인과 협의하세요.\n\n\n");
        }
        result.setComment(comment.toString());
        return result;
    }

    @Getter
    @Setter
    public class Result {
        private String result;
        private String comment;
    }
}