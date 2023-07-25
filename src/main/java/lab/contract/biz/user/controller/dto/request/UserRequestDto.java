package lab.contract.biz.user.controller.dto.request;

import lab.contract.biz.user.persistence.entity.User;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class UserRequestDto {
    //@NotBlank(message = "아이디는 필수 입력 값입니다.")
    private Long id;
    @Size(min = 2,max = 10,message = "최소 2자 이상, 10자 이하의 문자를 입력하세요.")
    private String username;
    @Email
    private String email;
    private String password;
    private String privacy_agreement_yn;

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .privacy_agreement_yn(privacy_agreement_yn)
                .build();
    }


    @Builder
    public UserRequestDto(String username,String email,String password, String privacy_agreement_yn) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.privacy_agreement_yn = privacy_agreement_yn;
    }
}
