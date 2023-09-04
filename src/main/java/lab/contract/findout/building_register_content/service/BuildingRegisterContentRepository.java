package lab.contract.findout.building_register_content.service;

import lab.contract.findout.building_register_content.persistence.BuildingRegisterContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRegisterContentRepository extends JpaRepository<BuildingRegisterContent,Long> {
}
