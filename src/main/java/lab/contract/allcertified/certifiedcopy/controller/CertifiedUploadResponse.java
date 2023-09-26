package lab.contract.allcertified.certifiedcopy.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertifiedUploadResponse {
    private int pages;
    private Object[] urls;
    @Builder
    public CertifiedUploadResponse(int pages, Object[] urls){
        this.pages = pages;
        this.urls = urls;
    }
}
