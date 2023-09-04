package lab.contract.findout.certifiedcopy_content.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedcopyContentRepository extends JpaRepository<CertifiedcopyContent, Long> {
}
