package lab.contract.allbuilding.building_register.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class BuildingRegister {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_register_id")
    private Long id;

    @OneToOne(mappedBy = "building_register", cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @OneToOne
    @JoinColumn(name = "building_register_content_id")
    private BuildingRegisterContent buildingRegisterContent;

    public void setBuildingRegisterContent(BuildingRegisterContent buildingRegisterContent) {
        this.buildingRegisterContent = buildingRegisterContent;
    }
    @Builder
    public BuildingRegister(Contract contract, LocalDateTime created_at) {
        this.contract = contract;
        this.created_at = created_at;
    }
}
