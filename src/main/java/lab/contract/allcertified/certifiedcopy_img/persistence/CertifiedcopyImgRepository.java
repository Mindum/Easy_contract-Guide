package lab.contract.allcertified.certifiedcopy_img.persistence;

import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedcopyImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedcopyImgRepository extends JpaRepository<CertifiedcopyImg, Long> {
}
