package lab.contract.infrastructure.exception.contract;

import lombok.Getter;

@Getter
public class IdNullException extends RuntimeException {
    private String responseMessage;

    public IdNullException (String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
}
