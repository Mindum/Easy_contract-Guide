package lab.contract.findout.certifiedcopy_content.persistence;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@Data
public class CertifiedcopyContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "certifiedcopy_id", nullable = false)
    private Long certifiedCopy_id;

    @Column(name = "total_address", nullable = false)
    private String total_address;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "street_address", nullable = false)
    private String street_address;

    @Column(name = "register_purpose")
    private String register_purpose;

    @Column(name = "etc")
    private String etc;
}
