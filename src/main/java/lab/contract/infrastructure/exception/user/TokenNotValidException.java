package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class TokenNotValidException extends RuntimeException{
    private String responseMessage;

    public TokenNotValidException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
