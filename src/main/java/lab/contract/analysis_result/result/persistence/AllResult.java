package lab.contract.analysis_result.result.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lab.contract.allcontract.contract.persistence.Contract;
import lab.contract.analysis_result.result.controller.ContractResultResponseDto;
import lab.contract.analysis_result.result_field.persistence.BuildingRegisterResultField;
import lab.contract.analysis_result.result_field.persistence.CertifiedResultField;
import lab.contract.analysis_result.result_field.persistence.ResultField;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class AllResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "all_result_id")
    Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @JsonIgnore
    @OneToMany(mappedBy = "all_result")
    private List<ResultField> result_field = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "all_result")
    private List<BuildingRegisterResultField> building_register_result = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "all_result")
    private List<CertifiedResultField> certifiedcopy_result = new ArrayList<>();

    private int rate;

    public void addResultField (ResultField resultField) { result_field.add(resultField);}
    public void addBuildingRegisterResult (BuildingRegisterResultField resultField) { building_register_result.add(resultField);}
    public void addCertifiedcopyResult (CertifiedResultField resultField) { certifiedcopy_result.add(resultField);}
    public void setRate (int rate) {this.rate = rate;}

    @Builder
    public AllResult (Contract contract){
        this.contract = contract;
    }

}
