package lab.contract.infrastructure.exception.contract;

import lombok.Getter;

@Getter
public class FileConversionException extends RuntimeException {
    private String responseMessage;

    public FileConversionException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}