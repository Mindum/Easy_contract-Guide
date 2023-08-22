package lab.contract.allcertified.certifiedcopy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedCopyRepository extends JpaRepository<CertifiedCopy, Long> {
}
