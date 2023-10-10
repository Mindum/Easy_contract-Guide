package lab.contract.findout.contract_content.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ContractContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_content_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id")
    private Contract contract;
    String address;
    String purpose;
    String rental_part;
    String deposit;
    @Column(length = 1000)
    String special_option;
    String lessor_address;
    String lessor_resident_number;
    String lessor_name;

    @Builder
    public ContractContent(Contract contract,String address,String purpose,String rental_part,
                           String deposit,String special_option,String lessor_address,String lessor_resident_number,String lessor_name) {
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
