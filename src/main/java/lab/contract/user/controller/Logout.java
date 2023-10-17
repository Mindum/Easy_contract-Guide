package lab.contract.user.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Logout {
    private String accessToken;
    private String refreshToken;
}
