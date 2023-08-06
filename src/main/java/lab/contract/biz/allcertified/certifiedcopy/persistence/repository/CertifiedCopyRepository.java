package lab.contract.biz.allcertified.certifiedcopy.persistence.repository;

import lab.contract.biz.allcertified.certifiedcopy.persistence.entity.CertifiedCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedCopyRepository extends JpaRepository<CertifiedCopy, Long> {
}
