package lab.contract.biz.allcertified.certifiedcopy.persistence.entity;

import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import lab.contract.biz.user.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class CertifiedCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifiedcopy_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Builder
    public CertifiedCopy(User user, Contract contract, LocalDateTime created_at) {
        this.user = user;
        this.contract = contract;
        this.created_at = created_at;
    }
}
