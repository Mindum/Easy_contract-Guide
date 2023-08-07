package lab.contract.biz.allcertified.certifiedcopy_img.persistence.enitty;

import lab.contract.biz.allcertified.certifiedcopy.persistence.entity.Certifiedcopy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class CertifiedcopyImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifiedcopy_img_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certifiedcopy_id")
    private Certifiedcopy certifiedcopy;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public CertifiedcopyImg(Certifiedcopy certifiedcopy,Integer page,String url) {
        this.certifiedcopy = certifiedcopy;
        this.page = page;
        this.url = url;
    }
}
