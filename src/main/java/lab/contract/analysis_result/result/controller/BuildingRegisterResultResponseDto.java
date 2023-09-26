package lab.contract.analysis_result.result.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingRegisterResultResponseDto {
    private Object[] resultFields;

    @Builder
    public BuildingRegisterResultResponseDto(Object[] resultFields) {
        this.resultFields = resultFields;
    }
}
