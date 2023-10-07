package lab.contract.user.controller;

import lab.contract.user.persistence.User;
import lab.contract.user.security.Authority;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class UserRequestDto {
    @Size(min = 2,max = 10,message = "최소 2자 이상, 10자 이하의 문자를 입력하세요.")
    private String username;
    @Email
    private String email;
    private String password;
    private String privacy_agreement_yn;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .privacy_agreement_yn(privacy_agreement_yn)
                .build();
    }
}
