package lab.contract.allbuilding.building_register_img.service;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allbuilding.building_register.persistence.BuildingRegisterRepository;
import lab.contract.allbuilding.building_register_img.persistence.BuildingRegisterImg;
import lab.contract.allbuilding.building_register_img.persistence.BuildingRegisterImgRepository;
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.openapi.clovaocr.TemplateOCR;
import lab.contract.openapi.convert.ConvertAPI;
import lab.contract.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingRegisterImgService {
    private final BuildingRegisterRepository buildingRegisterRepository;
    private final BuildingRegisterImgRepository buildingRegisterImgRepository;
    private final ConvertAPI convertAPI;
    private final TemplateOCR templateOCR;
    private final S3UploadService s3UploadService;
    private final ContractImgService contractImgService;
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

    public ArrayList<String[]> saveBuildingRegisterImg(Long buildingRegisterId, String pdfFileName) throws IOException {

        BuildingRegister buildingRegister = buildingRegisterRepository.findById(buildingRegisterId).orElseThrow(EntityNotFoundException::new);

        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();
        ArrayList<String[]> content = new ArrayList<>();
        String[] url = new String[pagesOfPdf];
        String name;

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                name = pdfFileName + ".png";
                MultipartFile multipartFile = contractImgService.transferMultipart(name);
                String s3url = s3UploadService.putPngFile(multipartFile);
                url[i-1] = s3url;
                buildingRegisterImgRepository.save(BuildingRegisterImg.builder()
                        .buildingRegister(buildingRegister)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            } else {
                name = pdfFileName + "-" + i + ".png";
                MultipartFile multipartFile = contractImgService.transferMultipart(name);
                String s3url = s3UploadService.putPngFile(multipartFile);
                url[i-1] = s3url;
                buildingRegisterImgRepository.save(BuildingRegisterImg.builder()
                        .buildingRegister(buildingRegister)
                        .page(i)
                        .url(DOWNLOAD_PATH + name).build());
                content.addAll(templateOCR.ocr(name));
            }
        }
        content.add(0,url);
        return content;
    }
}
