package lab.contract.user.persistence;

<<<<<<< HEAD
=======
import lab.contract.user.persistence.User;
>>>>>>> 22d1b5188f1d96f23a6924f66dd37086cb08b8c7
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
