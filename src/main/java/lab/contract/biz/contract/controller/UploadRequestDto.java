package lab.contract.biz.contract.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UploadRequestDto {
    private Long userId;
    private MultipartFile pdfFile;
}
