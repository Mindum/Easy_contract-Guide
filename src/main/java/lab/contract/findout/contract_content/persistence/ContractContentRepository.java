package lab.contract.findout.contract_content.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractContentRepository extends JpaRepository<ContractContent,Long> {
    Optional<ContractContent> findByContractId(Long contractId);
}
