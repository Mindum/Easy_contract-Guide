package lab.contract.infrastructure.exception;

public class ResponseMessage {
    public static final String SUCCESS = "성공";
    public static final String DUPLICATED_USER = "이미 존재하는 회원입니다.";
    public static final String DOES_NOT_EXIST_USER = "존재하지 않는 회원입니다.";
    public static final String PASSWORD_MISMATCH = "비밀번호가 일치하지 않습니다.";
    public static final String FILE_SIZE_LIMIT = "파일 크기가 너무 큽니다.";
    public static final String SESSION_NULL = "로그인 사용자가 존재하지 않습니다.";
    public static final String ID_NULL = "아이디 값이 없습니다.";
    public static final String TOKEN_MISMATCH = "토큰 정보가 일치하지 않습니다.";
    public static final String TOKEN_NOT_EXIST = "토큰이 존재하지 않습니다.";
    public static final String TOKEN_NOT_VALID = "토큰이 유효하지 않습니다.";

}
