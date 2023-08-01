package lab.contract.biz.openapi.convert;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ConvertAPI {

    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    public void convertApi(String pdfname) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret("G62G8OZLpyIHRu4A");
        ConvertApi.convert("pdf", "png",
                new Param("File", Paths.get(UPLOAD_PATH + pdfname)),
                new Param("FileName", pdfname)
        ).get().saveFilesSync(Paths.get(DOWNLOAD_PATH));




        //String pdfSourcePath = "C:/contract/getpdf/";
        //String pngDestinationPath = "C:/contract/savepng/";

        //ConvertApi.convert("pdf", "png",
        //        new Param("File", Paths.get(pdfSourcePath + pdfname + ".pdf")),
        //        new Param("FileName", "png")
        //).get().saveFilesSync(Paths.get(pngDestinationPath));

        //return "C:/PNG저장할 파일 경로";
    }

    //public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
   //     String pdfname = "건축물대장-아리관";
    //    convert(pdfname);
    //}
}
