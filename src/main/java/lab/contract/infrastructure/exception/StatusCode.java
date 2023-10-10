package lab.contract.infrastructure.exception;

public class StatusCode {
    public static final int OK = 200;

    public static final int BAD_REQUEST =  400;
    public static final int  DUPLICATED_USER=  401;
    public static final int DOES_NOT_EXIST_USER =  401;
    public static final int PASSWORD_MISMATCH =  404;
    public static final int SESSION_NULL = 405;

    public static final int FILE_SIZE_LIMIT = 412;
    public static final int ID_NULL = 444;
}
