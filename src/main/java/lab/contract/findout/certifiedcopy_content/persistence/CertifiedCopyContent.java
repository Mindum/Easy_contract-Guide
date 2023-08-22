package lab.contract.findout.certifiedcopy_content.persistence;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
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
public class CertifiedCopyContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certified_copy_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certified_copy_id")
    private CertifiedCopy certifiedCopy;

    @Column(nullable = false)
    private String total_address;  // 전체 지번

    @Column(nullable = false)
    private String address;  // 소재 지번

    @Column(nullable = false)
    private String street_address;  // 도로명주소

    @Column(nullable = false)
    private String register_purpose;  // 등기목적

    @Column(nullable = false)
    private String etc;  // 기타사항

    @Builder
    public CertifiedCopyContent(CertifiedCopy certifiedCopy, String total_address, String address, String street_address, String register_purpose, String etc) {
        this.certifiedCopy = certifiedCopy;
        this.total_address = total_address;
        this.address = address;
        this.street_address = street_address;
        this.register_purpose = register_purpose;
        this.etc = etc;
    }
}
