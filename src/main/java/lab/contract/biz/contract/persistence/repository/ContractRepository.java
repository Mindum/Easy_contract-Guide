package lab.contract.biz.contract.persistence.repository;

import lab.contract.biz.contract.persistence.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
