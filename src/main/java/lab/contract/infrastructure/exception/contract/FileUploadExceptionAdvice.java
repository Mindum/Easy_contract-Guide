package lab.contract.infrastructure.exception.contract;

import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity handleMaxSizeException(SizeLimitExceededException exc, HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity(DefaultRes.res(StatusCode.FILE_SIZE_LIMIT, ResponseMessage.FILE_SIZE_LIMIT), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.info("파일을 다시 업로드해주세요.");
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ENTITY_NULL), HttpStatus.BAD_REQUEST);
    }

}
