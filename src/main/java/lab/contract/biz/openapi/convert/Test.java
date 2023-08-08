package lab.contract.biz.openapi.convert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONObject;
import org.json.JSONArray;

public class Test {

    public static void main(String[] args) {
        String apiURL = "https://6sr2868cg4.apigw.ntruss.com/custom/v1/23023/be2c28899ce07ee0c09f6cd5486f9844491202a3a43051c04a44fca5bf4117a3/general";
        String secretKey = "bWhrdXplRWZyak5mbGZEZUJQYWxYdG1DREhtVUJDdHc=";
        String imageFile = "C:\\biz\\contract\\savepng\\임대차.jpg";
        String outputTextFile = "C:\\biz\\contract\\text\\추출된_텍스트.txt";

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
            image.put("format", "jpg");
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
                System.out.println("\n");
            }
            br.close();

            // JSON 응답 데이터에서 텍스트 필드만 추출하여 저장
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray imagesArray = jsonResponse.getJSONArray("images");
            JSONObject firstImage = imagesArray.getJSONObject(0);
            JSONArray fieldsArray = firstImage.getJSONArray("fields");

            StringBuilder extractedText = new StringBuilder();
            for (int i = 0; i < fieldsArray.length(); i++) {
                JSONObject field = fieldsArray.getJSONObject(i);
                String inferText = field.getString("inferText");
                extractedText.append(inferText).append("\n");
                System.out.println(inferText);
            }

            // 추출된 텍스트를 텍스트 파일에 저장
            FileWriter writer = new FileWriter(outputTextFile);
            writer.write(extractedText.toString());
            writer.close();


        } catch (Exception e) {
            System.out.println(e);
        }
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
