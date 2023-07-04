package lab.contract.biz.user.persistence.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
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

}
