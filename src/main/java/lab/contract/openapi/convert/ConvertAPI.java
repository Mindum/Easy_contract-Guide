package lab.contract.openapi.convert;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

@Service
public class ConvertAPI {
    private static final String UPLOAD_PATH = "C:/contract/getpdf/";
    private static final String DOWNLOAD_PATH = "C:/contract/savepng/";

    @Value("${convert.secret-key}")
    private String secret;
    public void convertApi(String pdfname) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret(secret);
        ConvertApi.convert("pdf", "png",
                new Param("File", Paths.get(UPLOAD_PATH + pdfname)),
                new Param("FileName", pdfname)
        ).get().saveFilesSync(Paths.get(DOWNLOAD_PATH));
    }
}

