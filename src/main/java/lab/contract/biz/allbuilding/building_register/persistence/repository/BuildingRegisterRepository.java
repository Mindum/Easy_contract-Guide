package lab.contract.biz.allbuilding.building_register.persistence.repository;

import lab.contract.biz.allbuilding.building_register.persistence.entity.BuildingRegister;
import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRegisterRepository extends JpaRepository<BuildingRegister, Long> {
}
