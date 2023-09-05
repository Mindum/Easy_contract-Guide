package lab.contract.compare.analysis_result.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class AnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_result_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    String contract_result;
    String certifiedcopy_result;
    String building_register_result;

    @Builder
    public AnalysisResult(Contract contract, String contract_result, String certifiedcopy_result, String building_register_result) {
        this.contract = contract;
        this.contract_result = contract_result;
        this.certifiedcopy_result = certifiedcopy_result;
        this.building_register_result = building_register_result;
    }
}
