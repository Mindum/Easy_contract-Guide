package lab.contract.biz.user.service;

import lab.contract.biz.user.controller.dto.request.UserRequestDto;
import lab.contract.biz.user.controller.dto.response.UserResponse;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.persistence.repository.UserRepository;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.user.DoesNotExistUserException;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lab.contract.infrastructure.exception.user.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User saveUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicatedUserException("DuplicatedUserException", ResponseMessage.DUPLICATED_USER);
        }

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User saveUser = requestDto.toEntity();
        userRepository.save(saveUser);
        return saveUser;
    }

    public User login(String email, String password) {
        Optional<User> findUser = userRepository.findByEmail(email);

        findUser.orElseThrow(() -> new DoesNotExistUserException("DoesNotExistUserException", ResponseMessage.DOES_NOT_EXIST_USER));

        if (!passwordEncoder.matches(password, findUser.get().getPassword())) {
            throw new PasswordMismatchException("PasswordMismatchException",ResponseMessage.PASSWORD_MISMATCH);
        }
        return findUser.get();
    }
}
