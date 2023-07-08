package lab.contract.biz.user.persistence.repository;

import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.persistence.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

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
        List<User> users = userRepository.findAll();

        //then
        User user = users.get(0);
        assertThat(user.getUsername()).isEqualTo(username);
    }
}
