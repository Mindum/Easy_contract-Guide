package lab.contract.biz.openapi.convert;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

@Service
public class ConvertAPI {

    public static String convert(String pdfname) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret("G62G8OZLpyIHRu4A");
        String pdfSourcePath = "C:/Users/rhkr0/OneDrive/바탕 화면/get/";
        String pngDestinationPath = "C:/Users/rhkr0/OneDrive/바탕 화면/save/";

        ConvertApi.convert("pdf", "png",
                new Param("File", Paths.get(pdfSourcePath + pdfname + ".pdf")),
                new Param("FileName", "png")
        ).get().saveFilesSync(Paths.get(pngDestinationPath));

        return pngDestinationPath;
        //ConvertApi.convert("pdf","png",
        //        new Param("File", Paths.get("C:/PDF가져올 파일 경로"+pdfname+".pdf")),
        //        new Param("FileName","png")
        //).get().saveFilesSync(Paths.get("C:/PNG저장할 파일 경로"));

        //return "C:/PNG저장할 파일 경로";
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String pdfname = "건축물대장-아리관";
        convert(pdfname);
    }
}
