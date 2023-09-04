package lab.contract.findout.certifiedcopy_content.persistence;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CertifiedcopyContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifiedcopy_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "certifiedcopy_id")
    private Certifiedcopy certifiedcopy;

    String total_address;
    String street_address;
    String register_purpose;
    String owner_name;
    String owner_resident_number;
    String owner_address;
    String owner_part;
    String sharer_name;
    String sharer_resident_number;
    String sharer_address;
    String sharer_part;
    Long mortgage;
    @Builder
    public CertifiedcopyContent(
            Certifiedcopy certifiedcopy,
            String total_address,
            String street_address,
            String register_purpose,
            String owner_name,
            String owner_address,
            String owner_resident_number,
            String owner_part,
            String sharer_name,
            String sharer_resident_number,
            String sharer_address,
            String sharer_part,
            Long mortgage) {
        this.certifiedcopy = certifiedcopy;
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


}
