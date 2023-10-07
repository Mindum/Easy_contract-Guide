package lab.contract.analysis_result.result_field.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lab.contract.analysis_result.result.persistence.AllResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class BuildingRegisterResultField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_register_result_field_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    @JoinColumn(name = "all_result_id")
    private AllResult all_result;

    @Column(nullable = false)
    private String type;

    @Column(length = 10000)
    private String comment;

    @Builder
    public BuildingRegisterResultField(AllResult allResult, String type, String comment) {
        this.all_result = allResult;
        this.type = type;
        this.comment = comment;
    }
}
