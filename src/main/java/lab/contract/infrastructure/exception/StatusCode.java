package lab.contract.infrastructure.exception;

public class StatusCode {
    public static final int OK = 200;

    public static final int BAD_REQUEST =  400;
    public static final int DUPLICATED_USER =  401;
    public static final int DOES_NOT_EXIST_USER =  401;
    public static final int PASSWORD_MISMATCH =  404;

    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;

    public static final int FILE_SIZE_LIMIT_EXCEEDED = 413;
    public static final int INVALID_FILE_EXTENSION = 415;
    public static final int FILE_CONVERSION_FAILED = 816;

}
