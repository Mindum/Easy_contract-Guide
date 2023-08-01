package lab.contract.biz.contract.persistence.entity;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lab.contract.biz.user.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    //@OneToMany(mappedBy = "contract")
    //private List<ContractImg> contractImg;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contract_name;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Builder
    public Contract(User user, String contract_name, LocalDateTime created_at) {
        this.user = user;
        this.contract_name = contract_name;
        this.created_at = created_at;
    }
}
