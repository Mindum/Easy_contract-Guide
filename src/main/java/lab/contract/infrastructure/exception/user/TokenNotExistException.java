package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class TokenNotExistException extends RuntimeException{
    private String responseMessage;

    public TokenNotExistException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
