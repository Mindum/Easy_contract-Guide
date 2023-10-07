package lab.contract.user.service;

import lab.contract.user.controller.UserRequestDto;
import lab.contract.user.controller.UserResponseDto;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.user.DoesNotExistUserException;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lab.contract.infrastructure.exception.user.PasswordMismatchException;
import lab.contract.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getIdAndUsername(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

//    @Transactional
//    public User saveUser(UserRequestDto requestDto) {
//        if (userRepository.existsByEmail(requestDto.getEmail())) {
//            throw new DuplicatedUserException("DuplicatedUserException", ResponseMessage.DUPLICATED_USER);
//        }
//
//        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
//        User saveUser = requestDto.toUser(passwordEncoder);
//        userRepository.save(saveUser);
//        return saveUser;
//    }
//
//    public User login(String email, String password) {
//        Optional<User> findUser = userRepository.findByEmail(email);
//
//        findUser.orElseThrow(() -> new DoesNotExistUserException("DoesNotExistUserException", ResponseMessage.DOES_NOT_EXIST_USER));
//
//        if (!passwordEncoder.matches(password, findUser.get().getPassword())) {
//            throw new PasswordMismatchException("PasswordMismatchException",ResponseMessage.PASSWORD_MISMATCH);
//        }
//        return findUser.get();
//    }
}



