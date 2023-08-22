package lab.contract.allbuilding.building_register.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRegisterRepository extends JpaRepository<BuildingRegister, Long> {
}
