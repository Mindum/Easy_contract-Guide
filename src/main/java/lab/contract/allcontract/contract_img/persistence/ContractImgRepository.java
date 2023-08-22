package lab.contract.allcontract.contract_img.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractImgRepository extends JpaRepository<ContractImg,Long> {

}
