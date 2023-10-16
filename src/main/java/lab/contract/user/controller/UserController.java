package lab.contract.user.controller;

import lab.contract.user.security.AuthService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lab.contract.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, authService.signup(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm) {

        UserResponseDto responseDto = userService.getIdAndUsername(loginForm.getEmail());
        LoginResponse loginResponse = LoginResponse.builder()
                .id(responseDto.getId())
                .username(responseDto.getUsername())
                .tokenDto(authService.login(loginForm))
                .build();

        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, loginResponse), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody Reissue reissue) {
        return authService.reissue(reissue);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Logout logout) {
        return authService.logout(logout);
    }
}