package lab.contract.analysis_result.result_field.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lab.contract.analysis_result.result.controller.ContractResultResponseDto;
import lab.contract.analysis_result.result.persistence.AllResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class ResultField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_field_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    @JoinColumn(name = "all_result_id")
    private AllResult all_result;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;

    @Column(length = 10000)
    private String comment;

    @Builder
    public ResultField(AllResult allResult,String title, String type, String comment) {
        this.all_result = allResult;
        this.title = title;
        this.type = type;
        this.comment = comment;
    }
}
