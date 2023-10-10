package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class SessionNullException extends RuntimeException{
    private String responseMessage;

    public SessionNullException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
