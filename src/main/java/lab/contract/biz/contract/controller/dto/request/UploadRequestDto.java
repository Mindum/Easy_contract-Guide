package lab.contract.biz.contract.controller;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class UploadRequestDto {

    private Long userId;
    private String contractName;
    private MultipartFile pdfFile;
}