package lab.contract.user.security;

import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lab.contract.infrastructure.exception.user.DuplicatedUserException;
import lab.contract.infrastructure.exception.user.TokenMismatchException;
import lab.contract.infrastructure.exception.user.TokenNotExistException;
import lab.contract.infrastructure.exception.user.TokenNotValidException;
import lab.contract.user.controller.*;
import lab.contract.user.jwt.TokenDto;
import lab.contract.user.jwt.TokenProvider;
import lab.contract.user.persistence.User;
import lab.contract.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    public UserResponseDto signup(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicatedUserException("DuplicatedUserException", ResponseMessage.DUPLICATED_USER);
        }

        User user = requestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    public TokenDto login(LoginForm loginForm) {
        UsernamePasswordAuthenticationToken authenticationToken = loginForm.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),tokenDto.getTokenExpiresIn(), TimeUnit.MICROSECONDS);
        return tokenDto;
    }
    public ResponseEntity<?> reissue(Reissue reissue) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(reissue.getRefreshToken())) {
            log.info("refresh token 검증 실패");
            throw new TokenNotValidException("TokenNotValidException",ResponseMessage.TOKEN_NOT_VALID);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(reissue.getAccessToken());
        log.info(authentication.getName());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if(ObjectUtils.isEmpty(refreshToken)) {
            log.info("refresh token이 존재하지 않음");
            throw new TokenNotExistException("TokenNotExistExcepion",ResponseMessage.TOKEN_NOT_EXIST);
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            log.info("refresh token 정보가 잘못됨");
            throw new TokenMismatchException("TokenMismatchException",ResponseMessage.TOKEN_MISMATCH);
        }
        // 4. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, tokenDto), HttpStatus.OK);
    }

    public ResponseEntity<?> logout(Logout logout) {
        // 1. Access Token 검증
        if (!tokenProvider.validateToken(logout.getAccessToken())) {
            throw new TokenNotValidException("TokenNotValidException",ResponseMessage.TOKEN_NOT_VALID);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = tokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
    }
}
