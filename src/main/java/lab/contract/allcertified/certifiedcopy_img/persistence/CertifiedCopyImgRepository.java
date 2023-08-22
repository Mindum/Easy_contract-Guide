package lab.contract.allcertified.certifiedcopy_img.persistence;

import lab.contract.allcertified.certifiedcopy_img.persistence.CertifiedCopyImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedCopyImgRepository extends JpaRepository<CertifiedCopyImg,Long> {
}
