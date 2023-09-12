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
    private int pages;
    private Object[] urls;
    @Builder
    public ContractUploadResponse(Long contractId, int pages, Object[] urls){
        this.contractId = contractId;
        this.pages = pages;
        this.urls = urls;
    }
}
