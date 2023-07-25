package lab.contract.biz.contract_img.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractImgRepository extends JpaRepository<ContractImg,Long> {

}
