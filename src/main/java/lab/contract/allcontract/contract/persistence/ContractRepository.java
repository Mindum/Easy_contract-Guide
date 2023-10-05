<<<<<<< HEAD
package lab.contract.allcontract.contract.persistence;

=======

package lab.contract.allcontract.contract.persistence;


import lab.contract.allcontract.contract.persistence.Contract;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

}
