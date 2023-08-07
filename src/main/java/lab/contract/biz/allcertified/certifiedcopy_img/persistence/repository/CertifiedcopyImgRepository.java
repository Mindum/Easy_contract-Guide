package lab.contract.biz.allcertified.certifiedcopy_img.persistence.repository;

import lab.contract.biz.allcertified.certifiedcopy_img.persistence.enitty.CertifiedcopyImg;
import lab.contract.biz.allcontract.contract.persistence.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifiedcopyImgRepository extends JpaRepository<CertifiedcopyImg, Long> {
}
