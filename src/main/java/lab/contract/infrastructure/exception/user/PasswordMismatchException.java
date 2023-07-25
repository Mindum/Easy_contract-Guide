package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException{
    private String responseMessage;

    public PasswordMismatchException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}