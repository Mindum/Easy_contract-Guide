package lab.contract.allcertified.certifiedcopy_img.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedcopyImgRepository extends JpaRepository<CertifiedcopyImg, Long> {
}
