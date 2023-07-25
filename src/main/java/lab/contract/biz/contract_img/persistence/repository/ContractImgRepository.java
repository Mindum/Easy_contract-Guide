package lab.contract.biz.contract_img.persistence.repository;

import lab.contract.biz.contract_img.persistence.entity.ContractImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractImgRepository extends JpaRepository<ContractImg,Long> {
    List<ContractImg> findByContractIdAndPage(Long contract_id, Integer page);
}
