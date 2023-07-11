package lab.contract.biz.user.service;

import lab.contract.biz.user.controller.dto.request.UserRequestDto;
import lab.contract.biz.user.service.UserService;
import lab.contract.biz.user.persistence.repository.UserRepository;
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

    @Test
    public void 회원가입_테스트() {
        //given
        UserRequestDto userRequestDto = createUser();
        //when
        Long saveId = userService.saveUser(userRequestDto);
        String saveEmail = userRepository.findById(saveId).get().getEmail();
        //then
        assertThat(userRequestDto.getEmail()).isEqualTo(saveEmail);
    }
    @Test
    public void 중복회원예외() {
        //given
        UserRequestDto userRequestDto1 = createUser();
        UserRequestDto userRequestDto2 = createUser();
        userService.saveUser(userRequestDto1);

        //when
        IllegalArgumentException returnStatusMessage = assertThrows(IllegalArgumentException.class,
                ()->userService.saveUser(userRequestDto2));
        //then
        assertThat(returnStatusMessage.getMessage()).isEqualTo("이미 가입된 회원입니다.");
    }
}