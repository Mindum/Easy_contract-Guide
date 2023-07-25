package lab.contract.biz.contract.persistence.entity;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "contract")
    private List<ContractImg> contractImg;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String contract_name;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;
}
