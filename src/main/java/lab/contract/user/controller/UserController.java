package lab.contract.user.controller;

import lab.contract.infrastructure.exception.user.SessionNullException;
import lab.contract.user.persistence.User;
import lab.contract.user.security.AuthService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lab.contract.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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
}