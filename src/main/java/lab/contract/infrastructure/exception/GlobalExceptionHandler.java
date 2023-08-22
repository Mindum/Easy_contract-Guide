package lab.contract.infrastructure.exception;

import lab.contract.infrastructure.exception.contract.FileSizeLimitExceededException;
import lab.contract.infrastructure.exception.user.DoesNotExistUserException;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lab.contract.infrastructure.exception.user.PasswordMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedUserException.class)
    public final ResponseEntity handleDuplicatedUserException(
            DuplicatedUserException e) {
        log.info("이미 존재하는 회원입니다.");
        return new ResponseEntity(DefaultRes.res(StatusCode.DUPLICATED_USER, e.getResponseMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoesNotExistUserException.class)
    public final ResponseEntity handleDoesNotExistUserException(
            DoesNotExistUserException e) {
        log.info("존재하지 않는 회원입니다.");
        return new ResponseEntity(DefaultRes.res(StatusCode.DOES_NOT_EXIST_USER, e.getResponseMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public final ResponseEntity handlePasswordMismatchException(
            PasswordMismatchException e) {
        log.info("비밀번호가 일치하지 않습니다.");
        return new ResponseEntity(DefaultRes.res(StatusCode.PASSWORD_MISMATCH, e.getResponseMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public final ResponseEntity handleFileSizeLimitExceededException(
            FileSizeLimitExceededException e) {
        log.info("파일 사이즈가 10MB를 넘습니다.");
        return new ResponseEntity(DefaultRes.res(StatusCode.FILE_SIZE_LIMIT_EXCEEDED, e.getResponseMessage()), HttpStatus.BAD_REQUEST);
    }

}
