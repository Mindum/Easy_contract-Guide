package lab.contract.allcontract.contract.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
