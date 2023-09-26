package lab.contract.analysis_result.result_field.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultFieldRepository extends JpaRepository<ResultField,Long> {
}
