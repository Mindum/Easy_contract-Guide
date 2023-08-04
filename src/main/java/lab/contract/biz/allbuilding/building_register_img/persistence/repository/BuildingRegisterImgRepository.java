package lab.contract.biz.allbuilding.building_register_img.persistence.repository;

import lab.contract.biz.allbuilding.building_register_img.persistence.entity.BuildingRegisterImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRegisterImgRepository extends JpaRepository<BuildingRegisterImg,Long> {
}
