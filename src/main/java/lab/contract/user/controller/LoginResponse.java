package lab.contract.user.controller;

import lab.contract.user.jwt.TokenDto;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class LoginResponse {
    private Long id;
    private String username;
    private TokenDto tokenDto;
    @Builder
    public LoginResponse(Long id, String username,TokenDto tokenDto) {
        this.id = id;
        this.username = username;
        this.tokenDto = tokenDto;
    }
}
