package lab.contract.analysis_result.result.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractResultResponseDto {
    private Object[] resultFields;
    @Builder
    public ContractResultResponseDto(Object[] resultFields) {
        this.resultFields = resultFields;
    }
}
