package lab.contract.findout.building_register_content.service;

import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContentRepository;
import lab.contract.openapi.clovaocr.GeneralOCR;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingRegisterContentService {

    private final BuildingRegisterContentRepository buildingRegisterContentRepository;
    private final GeneralOCR generalOCR;

    public BuildingRegisterContent saveContentToDB(String imageName) {
        String ocrResult = generalOCR.ocrapi(imageName);
        BuildingRegisterContent buildingRegisterContent = contentDivision(ocrResult);
        BuildingRegisterContent savedBuildingRegisterContent = buildingRegisterContentRepository.save(buildingRegisterContent);
        return savedBuildingRegisterContent;
    }

    public BuildingRegisterContent contentDivision(String text) {
        BuildingRegisterContent buildingRegisterContent = new BuildingRegisterContent();

        String[] lines = text.split("\n");
        String titleSectionContent = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String Testaddress = "";
            if (line.startsWith("명칭")) {
                titleSectionContent = extractValue(lines[i + 1]); // "명칭" 섹션 내용 저장
            } else if (line.startsWith("호명칭")) {
                buildingRegisterContent.setTitle(titleSectionContent); // "호명칭" 이전의 섹션 내용 저장
                buildingRegisterContent.setHo_title(extractValue(lines[i + 1])); // "호명칭" 섹션 내용 저장
            } else if (line.startsWith("대지위치")) {
                buildingRegisterContent.setLocation(extractValue(lines[i + 1]));
            } else if (line.startsWith("지번")) {
                buildingRegisterContent.setLocation_number(extractValue(lines[i + 1]));
            } else if (line.startsWith("도로명주소")) {
                buildingRegisterContent.setStreet_address(extractValue(lines[i + 1]));
            } else if (line.startsWith("부동산등기용등록번호")) {
                buildingRegisterContent.setOwner_name(extractValue(lines[i + 1]));
            } else if (line.startsWith("부동산등기용등록번호")) {
                buildingRegisterContent.setOwner_resident_number(extractValue(lines[i + 2]));
            } else if (line.startsWith("주소")) {
                StringBuilder addressBuilder = new StringBuilder();
                int j = i + 1; // 현재 줄 다음 줄부터 시작
                while (j < lines.length && !lines[j].startsWith("이하여백")) {
                    addressBuilder.append(extractValue(lines[j])).append(" ");
                    j++;
                }
                String fullAddress = addressBuilder.toString().trim();
                buildingRegisterContent.setOwner_address(fullAddress);
                i = j - 1;
                Testaddress = fullAddress;
            } else if (line.startsWith("지분")) {
                buildingRegisterContent.setOwner_part(extractValue(lines[i + 1]));
            } else if (line.startsWith("부동산등기용등록번호")) {
                buildingRegisterContent.setSharer_name(extractValue(lines[i + 3]));
            } else if (line.startsWith("부동산등기용등록번호")) {
                buildingRegisterContent.setSharer_resident_number(extractValue(lines[i + 4]));
            } else if (line.startsWith(Testaddress)) {
                StringBuilder ShareraddressBuilder = new StringBuilder();
                int j = i + 1; // 현재 줄 다음 줄부터 시작
                while (j < lines.length && !lines[j].startsWith("소유권")) {
                    ShareraddressBuilder.append(extractValue(lines[j])).append(" ");
                    j++;
                }
                String fullAddress = ShareraddressBuilder.toString().trim();
                buildingRegisterContent.setSharer_address(fullAddress);
                i = j - 1;
            } else if (line.startsWith("지분")){
                buildingRegisterContent.setSharer_part(extractValue(lines[i + 2]));
            }

        }

        return buildingRegisterContent;
    }

    private String extractValue(String line) { //실제 값만 뽑아서 사용할 수 있는 함수
        return line.substring(line.indexOf(" ") + 1).trim();
    }
}

