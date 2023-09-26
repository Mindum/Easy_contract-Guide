package lab.contract.allbuilding.building_register.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingUploadResponse {
    private int pages;
    private Object[] urls;
    @Builder
    public BuildingUploadResponse(int pages, Object[] urls){
        this.pages = pages;
        this.urls = urls;
    }
}
