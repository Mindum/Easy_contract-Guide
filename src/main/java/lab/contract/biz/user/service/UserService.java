package lab.contract.biz.user.service;

import lab.contract.biz.user.controller.dto.request.UserRequestDto;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long saveUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        return userRepository.save(requestDto.toEntity()).getId();
    }



}
