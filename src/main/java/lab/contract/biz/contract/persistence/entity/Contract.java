package lab.contract.biz.contract.persistence.entity;

<<<<<<< HEAD
import lab.contract.biz.contract_img.persistence.entity.ContractImg;
=======
>>>>>>> master
import lab.contract.biz.user.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
=======
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
>>>>>>> master

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

<<<<<<< HEAD
    //@OneToMany(mappedBy = "contract")
    //private List<ContractImg> contractImg;

=======
>>>>>>> master
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contract_name;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Builder
<<<<<<< HEAD
    public Contract(User user, String contract_name, LocalDateTime created_at) {
=======
    public Contract(User user,String contract_name,LocalDateTime created_at) {
>>>>>>> master
        this.user = user;
        this.contract_name = contract_name;
        this.created_at = created_at;
    }
<<<<<<< HEAD
=======

>>>>>>> master
}
