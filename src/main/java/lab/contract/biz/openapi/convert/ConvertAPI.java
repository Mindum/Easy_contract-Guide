package lab.contract.biz.openapi.convert;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class ConvertAPI {

    public static void convert(String pdfname) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret("G62G8OZLpyIHRu4A");
        ConvertApi.convert("pdf","png",
                new Param("File", Paths.get("C:/PDF가져올 파일 경로"+pdfname+".pdf")),
                new Param("FileName","png")
        ).get().saveFilesSync(Paths.get("C:/PNG저장할 파일 경로"));
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        String pdfname = "스프링입문";
        convert(pdfname);
    }

}
