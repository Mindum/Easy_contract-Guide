package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class DoesNotExistUserException extends RuntimeException{
    private String responseMessage;

    public DoesNotExistUserException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
