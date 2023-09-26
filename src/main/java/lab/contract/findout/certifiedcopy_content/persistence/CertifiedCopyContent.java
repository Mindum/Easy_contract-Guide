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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifiedcopy_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certifiedcopy_id")
    private CertifiedCopy certifiedCopy;

    String total_address;
    String street_address;
    String register_purpose;
    String owner_name;
    String owner_resident_number;
    String owner_address;
    Double owner_part;
    String sharer_name;
    String sharer_resident_number;
    String sharer_address;
    Double sharer_part;
    Long mortgage;
    @Builder
    public CertifiedCopyContent(CertifiedCopy certifiedCopy, String total_address, String street_address, String register_purpose, String owner_name, String owner_address, String owner_resident_number,Double owner_part, String sharer_name, String sharer_resident_number, String sharer_address, Double sharer_part, Long mortgage) {
        this.certifiedCopy = certifiedCopy;
        this.total_address = total_address;
        this.street_address = street_address;
        this.register_purpose = register_purpose;
        this.owner_name = owner_name;
        this.owner_resident_number = owner_resident_number;
        this.owner_address = owner_address;
        this.owner_part = owner_part;
        this.sharer_name = sharer_name;
        this.sharer_resident_number = sharer_resident_number;
        this.sharer_address = sharer_address;
        this.sharer_part = sharer_part;
        this.mortgage = mortgage;
    }

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certified_copy_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certified_copy_id")
    private CertifiedCopy certifiedCopy;

    @Column(nullable = true)
    private String total_address;  // 전체 지번

    //@Column(nullable = true)
    //private String address;  // 소재 지번

    @Column(nullable = true)
    private String street_address;  // 도로명주소

    @Column(nullable = true)
    private String register_purpose;  // 등기목적

    @Column(nullable = true)
    private String etc;  // 기타사항

    public void setTotalAddress(String total_address) {
        this.total_address = total_address;
    }

    //public void setAddress(String address) {
    //    this.address = address;
    //}

    public void setStreetAddress(String street_address) {
        this.street_address = street_address;
    }

    public void setEtc(String total_address) {
        this.total_address = total_address;
    }

    public void setRegisterPurpose(String register_purpose) {
        this.register_purpose = register_purpose;
    }

    @Builder
    public CertifiedCopyContent(CertifiedCopy certifiedCopy, String total_address, String address, String street_address, String register_purpose, String etc) {
        this.certifiedCopy = certifiedCopy;
        this.total_address = total_address;
        //this.address = address;
        this.street_address = street_address;
        this.register_purpose = register_purpose;
        this.etc = etc;
    }

 */
}
