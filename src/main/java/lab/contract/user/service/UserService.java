package lab.contract.user.service;

import lab.contract.user.controller.UserResponseDto;
import lab.contract.user.persistence.UserRepository;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.user.DoesNotExistUserException;
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
                .orElseThrow(() -> new DoesNotExistUserException("DoesNotExistUserException",ResponseMessage.DOES_NOT_EXIST_USER));
    }
}
