package lab.contract.analysis_result.result.controller;

import lab.contract.analysis_result.result_field.persistence.ResultField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ContractResultResponseDto {
    private int rate;
    private Object[] resultFields;
    @Builder
    public ContractResultResponseDto(int rate, Object[] resultFields) {
        this.rate = rate;
        this.resultFields = resultFields;
    }

}
