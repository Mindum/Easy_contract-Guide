package lab.contract.biz.allcertified.certifiedcopy_img.persistence.repository;

import lab.contract.biz.allbuilding.building_register_img.persistence.entity.BuildingImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractImgRepository extends JpaRepository<BuildingImg,Long> {

}
