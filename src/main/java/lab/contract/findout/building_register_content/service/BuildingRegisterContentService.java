package lab.contract.findout.building_register_content.service;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingRegisterContentService {

    private final BuildingRegisterContentRepository buildingRegisterContentRepository;


    private Long saveBuildingRegisterContent(BuildingRegister buildingRegister, String[][] buildingRegisterText)  {

        String title = "";
        String hoTitle ="";
        String location ="";
        String locationNumber = "";
        String streetAddress = "";
        String ownerName ="";
        String ownerRegisterNumber = "";
        String ownerAddress = "";
        String ownerPart = "";
        String sharerName = "";
        String sharerRegisterNumber = "";
        String sharerAddress = "";
        String sharerPart = "";

        for (int i = 0; i < buildingRegisterText.length; i++) {
            switch (buildingRegisterText[i][0]) {
                case "명칭":
                    title = extractContent("명칭", buildingRegisterText[i][1]);
                    System.out.println("title = " + title);
                    break;
                case "호명칭":
                    hoTitle = extractContent("호명칭", buildingRegisterText[i][1]);
                    System.out.println("hoTitle = " + hoTitle);
                    break;
                case "대지위치":
                    location = extractContent("대지위치", buildingRegisterText[i][1]);
                    System.out.println("location = " + location);
                    break;
                case "지번":
                    locationNumber = extractContent("지번", buildingRegisterText[i][1]);
                    System.out.println("locationNumber = " + locationNumber);
                    break;
                case "도로명주소":
                    streetAddress = extractContent("도로명주소", buildingRegisterText[i][1]);
                    System.out.println("streetAddress = " + streetAddress);
                    break;
                case "소유자 성명":
                    String text = buildingRegisterText[i][1];
                    String[] lines = text.split("\n");

                    String[] extractedValues = NameWithNumber(lines);
                    ownerName = extractedValues[0];
                    System.out.println("ownerName = " + ownerName);
                    ownerRegisterNumber = extractedValues[1];
                    System.out.println("ownerRegisterNumber = " + ownerRegisterNumber);
                    sharerName = extractedValues[2];
                    System.out.println("sharerName = " + sharerName);
                    sharerRegisterNumber = extractedValues[3];
                    System.out.println("sharerRegisterNumber = " + sharerRegisterNumber);
                    break;
                case "소유자 주소":
                    String alltext = buildingRegisterText[i][1];
                    ownerAddress = findAsEndWordContainEndWord(alltext, "주소", ")").trim();
                    ownerAddress = ownerAddress.replace("\n", "");
                    System.out.println("ownerAddress = " + ownerAddress);
                    if (sharerName == null) {
                        sharerAddress = null;
                    } else sharerAddress = findAsEndWordContainEndWord(alltext, ")", ")").trim();
                    sharerAddress = sharerAddress.replace("\n", "");
                    System.out.println("sharerAddress = " + sharerAddress);

                    break;
                case "소유권 지분":
                    ownerPart = extractContentAfterLineNumber(buildingRegisterText[i][1], 2);
                    System.out.println("ownerPart = " + ownerPart);
                    sharerPart = extractContentAfterLineNumber(buildingRegisterText[i][1], 3);
                    System.out.println("sharerPart = " + sharerPart);
                    break;
            }
        }

        BuildingRegisterContent saveBuildingRegisterContent = BuildingRegisterContent.builder()
                .buildingRegister(buildingRegister)
                .title(title)
                .ho_title(hoTitle)
                .location(location)
                .location_number(locationNumber)
                .street_address(streetAddress)
                .owner_name(ownerName)
                .owner_resident_number(ownerRegisterNumber)
                .owner_address(ownerAddress)
                .owner_part(ownerPart)
                .sharer_name(sharerName)
                .sharer_resident_number(sharerRegisterNumber)
                .sharer_address(sharerAddress)
                .sharer_part(sharerPart)
                .build();
        buildingRegister.setBuildingRegisterContent(saveBuildingRegisterContent);
        return buildingRegisterContentRepository.save(saveBuildingRegisterContent).getId();
    }

    public static String extractContent(String key, String text) {
        text = text.replace(key, "");
        return text.trim();
    }

    public static String extractContentAfterLineNumber(String text, int afterLine) {
        String[] lines = text.split("\n");
        String content = null;

        if (afterLine >= 0 && afterLine < lines.length) {
            content = lines[afterLine].trim();
        }
        if (content == null) {
            return null;
        }

        return content;
    }

    public static String findAsEndWordContainEndWord(String alltext, String findKey, String endWord) {
        if (!containCheck(alltext, findKey)) return "찾는 단어가 존재하지 않습니다.";
        if (!containCheck(alltext, endWord)) return "찾는 단어가 존재하지 않습니다.";
        int startIndex = alltext.indexOf(findKey);
        int endIndex = alltext.indexOf(endWord, startIndex + 1);
        if (endIndex == -1) {
            return "찾는 단어가 존재하지 않습니다.";
        }

        String findValue = alltext.substring(startIndex + findKey.length(), endIndex + endWord.length());
        findValue = findValue.replace(findKey, "").trim();
        return findValue;
    }

    public static boolean containCheck(String alltext, String findKey) {
        if (alltext.contains(findKey)) return true;
        return false;
    }

    public static String[] NameWithNumber(String[] lines) {
        String ownerName = null;
        String ownerRegisterNumber = null;
        String sharerName = null;
        String sharerRegisterNumber = null;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].matches("\\d{6}\\-[1-6]\\d{6} | \\d{6}\\-[1-6]\\*{6} | \\d{6}\\-[1-6]")) {  // 정규표현식
                String prevLine = lines[i - 1];
                if (ownerName == null) {
                    ownerName = prevLine;
                    ownerRegisterNumber = lines[i].trim();
                } else if (sharerName == null) {
                    sharerName = prevLine;
                    sharerRegisterNumber = lines[i].trim();
                    break;
                }
            }
        }
        return new String[]{ownerName, ownerRegisterNumber, sharerName, sharerRegisterNumber};
    }
}
