package lab.contract.allcontract.contract.controller;

import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class ContractUploadResponse {
    private Long contractId;
    @Builder
    public ContractUploadResponse(Long contractId){
        this.contractId = contractId;
    }
}
