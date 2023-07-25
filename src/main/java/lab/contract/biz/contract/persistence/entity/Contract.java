package lab.contract.biz.contract.persistence.entity;

<<<<<<< HEAD
import lab.contract.biz.user.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
>>>>>>> dc295feeba39c9b79b29dff96dc70e8ec2147a16

import javax.persistence.*;
import java.time.LocalDateTime;

<<<<<<< HEAD
@EntityListeners(AuditingEntityListener.class)
=======
>>>>>>> dc295feeba39c9b79b29dff96dc70e8ec2147a16
@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;
=======
    private Long id;

    @Column(nullable = false)
    private Long user_id;
>>>>>>> dc295feeba39c9b79b29dff96dc70e8ec2147a16

    @Column(nullable = false)
    private String contract_name;

<<<<<<< HEAD
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Builder
    public Contract(User user,String contract_name,LocalDateTime created_at) {
        this.user = user;
        this.contract_name = contract_name;
        this.created_at = created_at;
    }
=======
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;
>>>>>>> dc295feeba39c9b79b29dff96dc70e8ec2147a16
}
