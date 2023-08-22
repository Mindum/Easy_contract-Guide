package lab.contract.allcertified.certifiedcopy_img.persistence;

import lab.contract.allcertified.certifiedcopy.persistence.CertifiedCopy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class CertifiedCopyImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certifiedcopy_id")
    private CertifiedCopy certifiedCopy;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public CertifiedCopyImg(CertifiedCopy certifiedCopy,Integer page, String url) {
        this.certifiedCopy = certifiedCopy;
        this.page = page;
        this.url = url;
    }
}
