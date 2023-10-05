package lab.contract.findout.contract_content.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.Optional;

@Repository
public interface ContractContentRepository extends JpaRepository<ContractContent,Long> {
    Optional<ContractContent> findByContractId(Long contractId);
=======
@Repository
public interface ContractContentRepository extends JpaRepository<ContractContent,Long> {
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
}
