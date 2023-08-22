package lab.contract.user.persistence;

import lab.contract.user.controller.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String privacy_agreement_yn;

    @Builder
    public User(String username,String email,String password, String privacy_agreement_yn) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.privacy_agreement_yn = privacy_agreement_yn;
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .privacy_agreement_yn(privacy_agreement_yn)
                .build();
    }

}
