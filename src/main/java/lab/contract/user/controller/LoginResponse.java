package lab.contract.user.controller;

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
    @Builder
    public LoginResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
