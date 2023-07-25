package lab.contract.biz.contract_img.persistence.controller.api;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.contract_img.persistence.service.ContractImgService;
import lab.contract.biz.user.session.SessionConstant;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class ContractImgController {
    private final ContractImgRepository contractImgRepository;
    private final ContractImgService contractImgService;

    @Autowired
    public ContractImgController(ContractImgRepository contractImgRepository, ContractImgService contractImgService) {
        this.contractImgRepository = contractImgRepository;
        this.contractImgService = contractImgService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(
            @RequestParam("contract_id") Long contract_id,
            @RequestParam("page") Integer page,
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String result = contractImgService.uploadFile(contract_id, page, file);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.SUCCESS);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.FILE_UPLOAD_FAIL);
        }
    }
}