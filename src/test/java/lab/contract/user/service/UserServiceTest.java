package lab.contract.user.service;

import lab.contract.user.controller.UserRequestDto;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lab.contract.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 자동 롤백
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserRequestDto createUser() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("홍길동");
        userRequestDto.setEmail("test@email.com");
        userRequestDto.setPassword("1234");
        userRequestDto.setPrivacy_agreement_yn("y");
        return userRequestDto;
    }


}