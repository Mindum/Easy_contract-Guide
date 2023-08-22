package lab.contract.findout.contract_content.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractContentRepository extends JpaRepository<ContractContent,Long> {
}
