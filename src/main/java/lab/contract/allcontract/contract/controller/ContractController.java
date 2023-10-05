<<<<<<< HEAD
=======

>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
package lab.contract.allcontract.contract.controller;

import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.allcontract.contract_img.service.ContractImgService;
<<<<<<< HEAD
import lab.contract.findout.contract_content.service.ContractContentService;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
=======
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
<<<<<<< HEAD
public class ContractController {
    private final ContractService contractService;
    private final ContractImgService contractImgService;
    private final ContractContentService contractContentService;
=======
public class ContractController implements HandlerExceptionResolver {
    private final ContractService contractService;
    private final ContractImgService contractImgService;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

    @PostMapping("/file/contract")
    public ResponseEntity fileUpload(
            ContractRequestDto contractRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(contractRequestDto.getUserId());
        String fileName = contractService.savePdfFile(contractRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
<<<<<<< HEAD
        List<String> list = contractImgService.saveContractImg(saveId,fileName);
        Object[] url = new Object[list.size()];
        for (int i=0;i< url.length;i++) {
            url[i] = list.get(i);
        }
        contractContentService.saveContractContent(saveId);
        ContractUploadResponse contractUploadResponse = ContractUploadResponse.builder()
                .contractId(saveId)
                .pages(url.length)
                .urls(url).build();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, contractUploadResponse), HttpStatus.OK);
    }
}
=======
        String con  = contractImgService.saveContractImg(saveId,fileName);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS,con), HttpStatus.OK);
    }
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        ModelAndView modelAndView = new ModelAndView("file");
        if (ex instanceof SizeLimitExceededException) {
            modelAndView.getModel().put("message", "File size exceeds limit!");
        }
        return modelAndView;
    }
}
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
