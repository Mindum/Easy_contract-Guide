package lab.contract.allcertified.certifiedcopy.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
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
public class Certifiedcopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifiedcopy_id")
    private Long id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Builder
    public Certifiedcopy(Contract contract, LocalDateTime created_at) {
        this.contract = contract;
        this.created_at = created_at;
    }
}
