package lab.contract.infrastructure.exception.contract;

import lombok.Getter;

@Getter
public class FileSizeLimitExceededException extends RuntimeException {
    private String responseMessage;

    public FileSizeLimitExceededException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }

}
