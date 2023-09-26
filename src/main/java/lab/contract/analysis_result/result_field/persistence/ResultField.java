package lab.contract.analysis_result.result_field.persistence;

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
    @JoinColumn(name = "all_result_id")
    private AllResult allResult;

    @Column(nullable = true)
    private String type;

    @Column(length = 10000)
    private String comment;

    @Builder
    public ResultField(AllResult allResult, String type, String comment) {
        this.allResult = allResult;
        this.type = type;
        this.comment = comment;
    }
}
