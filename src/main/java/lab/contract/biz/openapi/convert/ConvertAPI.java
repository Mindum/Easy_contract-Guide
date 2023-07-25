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

    public void convert(String pdfname) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret("G62G8OZLpyIHRu4A");
        ConvertApi.convert("pdf","png",
                new Param("File", Paths.get("C:/contract/getpdf/"+pdfname)),
                new Param("FileName",pdfname)
        ).get().saveFilesSync(Paths.get("C:/contract/savepng"));
    }

}
