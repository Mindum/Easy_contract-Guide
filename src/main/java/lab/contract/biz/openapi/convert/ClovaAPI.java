package lab.contract.biz.openapi.convert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ClovaAPI {
    public String ocrapi(String imagename) {
        String apiURL = "https://lvxcocb3a7.apigw.ntruss.com/custom/v1/22953/d1f1f9ab556b6371045222bdbb1d40bf937de8e078d40f656ba7601a6d50de22/general";
        String secretKey = "bWdZUnhOZ3dmaGZ1SHpicEJTdHhvcGRmbGt6bkFRQ0I=";

        //String imagename = "왜.png";
        String imagePath = Paths.get("C:/contract/savepng").toString();
        String imageFile = imagePath + "/" + imagename;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "png");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();


            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println(jsonObject);
            JSONArray imgArray = jsonObject.getJSONArray("images");
            JSONObject img = imgArray.getJSONObject(0);
            JSONArray fieldArray = img.getJSONArray("fields");
            //StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fieldArray.length(); i++) {
                JSONObject object = fieldArray.getJSONObject(i);
                String content = object.getString("inferText");
                sb.append(content).append(" ");
            }

            File textFile = new File("C:/contract/writeFile.txt");
            if (!textFile.exists()) {
                textFile.createNewFile();
            }
            FileWriter fw = new FileWriter(textFile);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(sb.toString());
            writer.close();
            //return sb.toString();

        } catch (Exception e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
}
