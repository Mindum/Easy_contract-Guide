package lab.contract.biz.contract_img.persistence.service;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.contract_img.persistence.repository.ContractImgRepository;
import lab.contract.biz.openapi.convert.ConvertAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ContractImgService {

    private final ConvertAPI convertAPI;
    private final ContractImgRepository contractImgRepository;

    @Autowired
    public ContractImgService(ConvertAPI convertAPI, ContractImgRepository contractImgRepository) {
        this.convertAPI = convertAPI;
        this.contractImgRepository = contractImgRepository;
    }

    public String uploadFile(Long contract_id, Integer page, MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        try {

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            String filePath = "C:/Users/rhkr0/OneDrive/바탕 화면/get/" + uniqueFileName;
            Path path = Paths.get(filePath);

            Files.write(path, file.getBytes());

            String convertedFileName = convertAPI.convert(filePath);

            // ContractImg 엔티티 생성 및 저장
            ContractImg contractImg = ContractImg.builder()
                    .contract_id(contract_id)
                    .page(page)
                    .url(convertedFileName) // 변환된 파일의 저장된 경로를 DB에 저장
                    .build();
            contractImgRepository.save(contractImg);

            return "파일 업로드 성공";
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "파일 업로드 실패";
        }
    }
}