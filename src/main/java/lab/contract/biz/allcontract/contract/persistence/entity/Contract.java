package lab.contract.biz.allcontract.contract.persistence.entity;

import lab.contract.biz.user.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contract_name;

    @Column(length = 10000)
    private String contract_content;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    public void update(String contract_content) {
        this.contract_content = contract_content;
    }

    @Builder
    public Contract(User user,String contract_name,LocalDateTime created_at) {
        this.user = user;
        this.contract_name = contract_name;
        this.created_at = created_at;
    }

}
