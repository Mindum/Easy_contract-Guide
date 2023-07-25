package lab.contract.biz.user.persistence.repository;

import lab.contract.biz.user.persistence.entity.User;
<<<<<<< Updated upstream
import lab.contract.biz.user.presentation.repository.UserRepository;
=======
>>>>>>> Stashed changes
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void DB연동_테스트(){

        //given
        String username = "테스트 네임";
        String email = "test@naver.com";
        String password = "1234";
        String privacy_agreement_yn = "y";

        userRepository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .privacy_agreement_yn(privacy_agreement_yn)
                .build());

        //when
        Optional<User> finduser = userRepository.findByEmail(email);

        //then

        assertThat(finduser.get().getUsername()).isEqualTo(username);
    }
}
