package lab.contract.biz.allbuilding.building_register_img.persistence.entity;

import lab.contract.biz.allbuilding.building_register.persistence.entity.BuildingRegister;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class BuildingRegisterImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_register_img_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "building_register_id")
    private BuildingRegister buildingRegister;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public BuildingRegisterImg(BuildingRegister buildingRegister,Integer page,String url) {
        this.buildingRegister = buildingRegister;
        this.page = page;
        this.url = url;
    }
}
