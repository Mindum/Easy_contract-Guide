package lab.contract.analysis_result.result.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
