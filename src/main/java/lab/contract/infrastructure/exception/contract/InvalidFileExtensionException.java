package lab.contract.infrastructure.exception.contract;

import lombok.Getter;

@Getter
public class InvalidFileExtensionException extends RuntimeException {
    private String responseMessage;

    public InvalidFileExtensionException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}