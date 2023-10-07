package lab.contract.allbuilding.building_register_img.service;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allbuilding.building_register.persistence.BuildingRegisterRepository;
import lab.contract.allbuilding.building_register_img.persistence.BuildingRegisterImg;
import lab.contract.allbuilding.building_register_img.persistence.BuildingRegisterImgRepository;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import lab.contract.allcontract.contract_img.service.ContractImgService;
>>>>>>> fef60a71732d959a9c113c490307bdb5402f4867
import lab.contract.openapi.clovaocr.TemplateOCR;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import lab.contract.openapi.convert.ConvertAPI;
import lab.contract.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
=======
import java.io.File;
import java.io.IOException;
import java.util.Optional;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingRegisterImgService {
    private final BuildingRegisterRepository buildingRegisterRepository;
    private final BuildingRegisterImgRepository buildingRegisterImgRepository;
    private final ConvertAPI convertAPI;
<<<<<<< HEAD
    private final TemplateOCR templateOCR;
<<<<<<< HEAD
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
=======
    private final S3UploadService s3UploadService;
    private final ContractImgService contractImgService;
>>>>>>> fef60a71732d959a9c113c490307bdb5402f4867
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertPdfToPng(String pdfFileName) throws IOException, ExecutionException, InterruptedException {
        convertAPI.convertApi(pdfFileName);
    }

<<<<<<< HEAD
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
=======
    public int saveBuildingRegisterImg(Long buildingRegisterId, String pdfFileName) throws IOException {

        Optional<BuildingRegister> buildingRegister = buildingRegisterRepository.findById(buildingRegisterId);
        if (!buildingRegister.isPresent()) {
            // contract 예외 처리
        }
        File source = new File(UPLOAD_PATH + pdfFileName);
        PDDocument document = PDDocument.load(source);
        int pagesOfPdf = document.getNumberOfPages();

        for (int i = 1; i <= pagesOfPdf; i++) {
            if (i == 1) {
                buildingRegisterImgRepository.save(BuildingRegisterImg.builder()
                        .buildingRegister(buildingRegister.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName).build());
            } else {
                buildingRegisterImgRepository.save(BuildingRegisterImg.builder()
                        .buildingRegister(buildingRegister.get())
                        .page(i)
                        .url(DOWNLOAD_PATH + pdfFileName + "_" + i).build());
            }
        }
        return pagesOfPdf;
    }

>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
}
