package lab.contract.allcontract.contract.controller;

import lab.contract.allcontract.contract.service.ContractService;
import lab.contract.allcontract.contract_img.service.ContractImgService;
import lab.contract.findout.contract_content.service.ContractContentService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class ContractController implements HandlerExceptionResolver {
    private final ContractService contractService;
    private final ContractImgService contractImgService;
    private final ContractContentService contractContentService;

    @PostMapping("/file/contract")
    public ResponseEntity fileUpload(
            ContractRequestDto contractRequestDto) throws IOException, ExecutionException, InterruptedException {
        Long saveId = contractService.saveContract(contractRequestDto.getUserId());
        String fileName = contractService.savePdfFile(contractRequestDto.getPdfFile());
        contractImgService.convertPdfToPng(fileName);
        contractImgService.saveContractImg(saveId,fileName);
        contractContentService.saveContractContent(saveId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
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
