
package lab.contract.biz.allcertified.certifiedcopy.persistence.repository;

import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

}
