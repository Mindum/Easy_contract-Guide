package lab.contract.openapi.clovaocr;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class TemplateOCR {
    public ArrayList<String[]> ocr(String imagename) {
        String apiURL = "https://lvxcocb3a7.apigw.ntruss.com/custom/v1/24325/5bf77f6a9ef3b4c215c5fee42e1743649634e1f002002d284595cfb696634a99/infer";
        String secretKey = "T2ZaTUd0UkJ0YWx1RWtOSWpZWUlNa1dpUklXYlp6WUM=";

        //String imagename = "5e7b701b-e91c-453d-9ae5-e2ee989fae1d_근저당권있는 등기부등본.pdf-4.png";
        String imagePath = Paths.get("C:/contract/savepng").toString();
        String imageFile = imagePath + "/" + imagename;
        ArrayList<String[]> text = new ArrayList<>();
        //String[][] text = new String[20][2];

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
            String[] template = {"25981","등기부등본2"};
            json.put("templateIds",template);
            json.put("enableTableDetection",true);
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
            //System.out.println("jsonObject = " + jsonObject);
            JSONArray imgArray = jsonObject.getJSONArray("images");
            JSONObject img = imgArray.getJSONObject(0);
            JSONArray fieldArray = img.getJSONArray("fields");
            for (int i = 0; i < fieldArray.length(); i++) {
                JSONObject object = fieldArray.getJSONObject(i);
                String key = object.getString("name");
                String content = object.getString("inferText");
                text.add(new String[]{key,content});
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return text;
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
