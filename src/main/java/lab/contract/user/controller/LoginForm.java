package lab.contract.user.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Valid
public class LoginForm {
    @Email
    private String email;
    private String password;
}
