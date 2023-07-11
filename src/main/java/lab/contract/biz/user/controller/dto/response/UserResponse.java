package lab.contract.biz.user.controller.dto.response;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String privacy_agreement_yn;

    @Builder
    public UserResponse(String username,String email,String password, String privacy_agreement_yn) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.privacy_agreement_yn = privacy_agreement_yn;
    }
}
