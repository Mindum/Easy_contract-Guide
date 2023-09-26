package lab.contract.findout.contract_content.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Component
public class ContractContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(nullable = false)
    private String address;  // 주소

    @Column(nullable = false)
    private String purpose;  // 건물-구조 용도

    @Column(nullable = false)
    private String rental_part;  // 임대할 부분

    @Column(nullable = false)
    private String deposit;  // 보증금

    @Column(length = 10000)
    private String special_option;  // 특약사항

    @Column(nullable = false)
    private String lessor_address;  // 임대인 주소

    @Column(nullable = false)
    private String lessor_resident_number;  // 임대인 주민등록번호

    @Column(nullable = false)
    private String lessor_name; // 임대인 성명

    @Builder
    public ContractContent(Contract contract, String address, String purpose, String rental_part, String deposit, String special_option, String lessor_address, String lessor_resident_number, String lessor_name) {
        this.contract = contract;
        this.address = address;
        this.purpose = purpose;
        this.rental_part = rental_part;
        this.deposit = deposit;
        this.special_option = special_option;
        this.lessor_address = lessor_address;
        this.lessor_resident_number = lessor_resident_number;
        this.lessor_name = lessor_name;
    }
}
