package lab.contract.biz.allcertified.certifiedcopy_img.persistence.repository;

import lab.contract.biz.allcertified.certifiedcopy_img.persistence.entity.CertifiedCopyImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedCopyImgRepository extends JpaRepository<CertifiedCopyImg,Long> {
}
