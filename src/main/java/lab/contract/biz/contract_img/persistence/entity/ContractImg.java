package lab.contract.biz.contract_img.persistence.entity;

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

    @Column(nullable = false)
    private Long contract_id;

    @Column(nullable = false)
    private Integer page;

    @Column(nullable = false)
    private String url;

    @Builder
    public ContractImg(Long contract_id,Integer page, String url) {
        this.contract_id = contract_id;
        this.page = page;
        this.url = url;
    }
}
