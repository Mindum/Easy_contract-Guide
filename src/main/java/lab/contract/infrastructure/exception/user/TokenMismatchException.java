package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class TokenMismatchException extends RuntimeException{
    private String responseMessage;

    public TokenMismatchException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
