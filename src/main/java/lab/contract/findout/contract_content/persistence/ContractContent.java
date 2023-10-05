package lab.contract.findout.contract_content.persistence;

import lab.contract.allcontract.contract.persistence.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ContractContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@Getter
public class ContractContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
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
<<<<<<< HEAD
    public ContractContent(Contract contract,String address,String purpose,String rental_part,
                           String deposit,String special_option,String lessor_address,String lessor_resident_number,String lessor_name) {
=======
    public ContractContent(Contract contract, String address, String purpose, String rental_part,
                           String deposit, String special_option, String lessor_address, String lessor_resident_number, String lessor_name) {
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
