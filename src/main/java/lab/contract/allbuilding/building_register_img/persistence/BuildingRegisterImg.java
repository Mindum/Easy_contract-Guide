package lab.contract.allbuilding.building_register_img.persistence;

import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class BuildingRegisterImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "building_register_id")
    private BuildingRegister buildingRegister;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public BuildingRegisterImg(BuildingRegister buildingRegister,Integer page, String url) {
        this.buildingRegister = buildingRegister;
        this.page = page;
        this.url = url;
    }
}
