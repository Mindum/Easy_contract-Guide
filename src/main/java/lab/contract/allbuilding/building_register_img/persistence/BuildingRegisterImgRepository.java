package lab.contract.allbuilding.building_register_img.persistence;

import lab.contract.allbuilding.building_register_img.persistence.BuildingRegisterImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRegisterImgRepository extends JpaRepository<BuildingRegisterImg, Long> {
}
