package lab.contract.biz.contract_img.persistence.entity;

import lab.contract.biz.contract.persistence.entity.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "contract_img")
public class ContractImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contract_id", insertable=false, updatable=false)
    private Contract contract;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public ContractImg(Contract contract,Integer page, String url) {
        this.contract = contract;
        this.page = page;
        this.url = url;
    }
}