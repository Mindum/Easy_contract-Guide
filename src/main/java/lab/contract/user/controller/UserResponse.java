package lab.contract.user.controller;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String privacy_agreement_yn;

    @Builder
    public UserResponse(Long id, String username,String email,String password, String privacy_agreement_yn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.privacy_agreement_yn = privacy_agreement_yn;
    }
}
