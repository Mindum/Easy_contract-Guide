package lab.contract.infrastructure.exception.user;

import lombok.Getter;

@Getter
public class DuplicatedUserException extends RuntimeException{

    private String responseMessage;

    public DuplicatedUserException(String message, String responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> master
