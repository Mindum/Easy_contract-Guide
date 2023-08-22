package lab.contract.allcertified.certifiedcopy.persistence;

import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedcopyRepository extends JpaRepository<Certifiedcopy, Long> {
}
