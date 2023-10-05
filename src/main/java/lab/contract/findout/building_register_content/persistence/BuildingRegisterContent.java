package lab.contract.findout.building_register_content.persistence;

<<<<<<< HEAD
import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@Getter
<<<<<<< HEAD
public class BuildingRegisterContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_register_content_id", nullable = false)
=======
@Setter
public class BuildingRegisterContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "building_register_id", nullable = false)
<<<<<<< HEAD
    private BuildingRegister buildingRegister;
=======
    private Contract contract;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

    @Column(name = "title")
    private String title;

    @Column(name = "ho_title")
    private String ho_title;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "location_number", nullable = false)
    private String location_number;

    @Column(name = "street_address", nullable = false)
    private String street_address;

    @Column(name = "owner_name", nullable = false)
    private String owner_name;

    @Column(name = "owner_resident_number", nullable = false)
    private String owner_resident_number;

    @Column(name = "owner_address", nullable = false)
    private String owner_address;

    @Column(name = "owner_part", nullable = false)
<<<<<<< HEAD
    private Double owner_part;
=======
    private String owner_part;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

    @Column(name = "sharer_name")
    private String sharer_name;

    @Column(name = "sharer_resident_number")
    private String sharer_resident_number;

    @Column(name = "sharer_address")
    private String sharer_address;

    @Column(name = "sharer_part")
<<<<<<< HEAD
    private Double sharer_part;
=======
    private String sharer_part;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public BuildingRegisterContent(
<<<<<<< HEAD
            BuildingRegister buildingRegister,
=======
            Contract contract,
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
            String title,
            String ho_title,
            String location,
            String location_number,
            String street_address,
            String owner_name,
            String owner_resident_number,
            String owner_address,
<<<<<<< HEAD
            Double owner_part,
            String sharer_name,
            String sharer_resident_number,
            String sharer_address,
            Double sharer_part,
            LocalDateTime createdAt
    ) {
        this.buildingRegister = buildingRegister;
=======
            String owner_part,
            String sharer_name,
            String sharer_resident_number,
            String sharer_address,
            String sharer_part,
            LocalDateTime createdAt
    ) {
        this.contract = contract;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
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
        this.createdAt = createdAt;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
