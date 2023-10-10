package lab.contract.findout.building_register_content.persistence;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.encryption.Aes256Converter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class BuildingRegisterContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_register_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "building_register_id")
    private BuildingRegister buildingRegister;

    @Column(nullable = true)
    @Convert(converter = Aes256Converter.class)
    private String title;  // 명칭

    @Column(nullable = true)
    @Convert(converter = Aes256Converter.class)
    private String ho_title;  // 호명칭

    @Column(nullable = false)
    private String location;  // 대지 위치

    @Column(nullable = false)
    private String location_number;  // 지번

    @Column(nullable = false)
    private String street_address;  // 도로명주소

    @Column(nullable = false)
    private String owner_name;  // 소유자 성명

    @Column(nullable = false)
    @Convert(converter = Aes256Converter.class)
    private String owner_resident_number;  // 소유자 주민등록번호

    @Column(nullable = false)
    @Convert(converter = Aes256Converter.class)
    private String owner_address; // 소유자 주소

    @Column(nullable = false)
    private Double owner_part;  // 소유자 지분

    @Column(nullable = true)
    private String sharer_name;  // 공유자 성명

    @Column(nullable = true)
    @Convert(converter = Aes256Converter.class)
    private String sharer_resident_number;  // 공유자 주민등록번호

    @Column(nullable = true)
    @Convert(converter = Aes256Converter.class)
    private String sharer_address;  // 공유자 주소

    @Column(nullable = true)
    private Double sharer_part; // 공유자 지분

    @Builder
    public BuildingRegisterContent(BuildingRegister buildingRegister, String title, String ho_title, String location, String location_number, String street_address, String owner_name,
                                   String owner_resident_number, String owner_address, Double owner_part, String sharer_name, String sharer_resident_number, String sharer_address, Double sharer_part) {
        this.buildingRegister = buildingRegister;
        this.title = title;
        this.ho_title = ho_title;
        this.location = location;
        this.location_number = location_number;
        this.street_address = street_address;
        this.owner_name = owner_name;
        this.owner_resident_number = owner_resident_number;
        this.owner_address = owner_address;
        this.owner_part = owner_part;
        this.sharer_name = sharer_name;
        this.sharer_resident_number = sharer_resident_number;
        this.sharer_address = sharer_address;
        this.sharer_part = sharer_part;
    }
}
