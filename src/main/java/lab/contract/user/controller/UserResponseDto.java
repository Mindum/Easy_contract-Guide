package lab.contract.user.controller;

import lab.contract.user.persistence.User;
import lombok.*;

import javax.validation.Valid;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String privacy_agreement_yn;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}