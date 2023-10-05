package lab.contract.allcontract.contract.persistence;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;
import lab.contract.allbuilding.building_register.persistence.BuildingRegister;
import lab.contract.allcertified.certifiedcopy.persistence.Certifiedcopy;
import lab.contract.allcontract.contract_img.persistence.ContractImg;
import lab.contract.analysis_result.result.persistence.AllResult;
import lab.contract.findout.contract_content.persistence.ContractContent;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import lab.contract.user.persistence.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contract_name;
<<<<<<< HEAD
    @JsonIgnore
    @OneToMany(mappedBy = "contract")
    private List<ContractImg> contract_imgs = new ArrayList<>();
=======
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7

    @Column(length = 10000)
    private String contract_text;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

<<<<<<< HEAD
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "contract_content_id")
    private ContractContent contract_content;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "certifiedcopy_id")
    private Certifiedcopy certifiedcopy;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "building_register_id")
    private BuildingRegister building_register;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "all_result_id")
    private AllResult all_result;

    public void addContractImg(ContractImg contractImg) {
        contract_imgs.add(contractImg);
    }
    public void setContract_text (String contract_text) {
        this.contract_text = contract_text;
    }
    public void setContractContent (ContractContent contract_content) {
        this.contract_content = contract_content;
    }
    public void setCertifiedcopy(Certifiedcopy certifiedcopy) {
        this.certifiedcopy = certifiedcopy;
    }
    public void setBuilding_register(BuildingRegister building_register) {
        this.building_register = building_register;
    }
    public void setAllResult(AllResult allResult) { this.all_result = allResult; }

    @Builder
    public Contract(User user,String contract_name,String contract_text,LocalDateTime created_at) {
        this.user = user;
        this.contract_name = contract_name;
        this.contract_text = contract_text;
=======
    public void update(String contract_text) {
        this.contract_text = contract_text;
    }

    @Builder
    public Contract(User user,String contract_name,LocalDateTime created_at) {
        this.user = user;
        this.contract_name = contract_name;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
        this.created_at = created_at;
    }

}
