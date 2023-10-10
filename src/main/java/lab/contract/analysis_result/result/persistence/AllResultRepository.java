package lab.contract.analysis_result.result.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllResultRepository extends JpaRepository<AllResult,Long> {
}
