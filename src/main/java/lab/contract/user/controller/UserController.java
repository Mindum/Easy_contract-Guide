package lab.contract.user.controller;

import lab.contract.user.persistence.User;
import lab.contract.user.service.UserService;
import lab.contract.user.session.SessionConstant;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Member;

@CrossOrigin(originPatterns = "*")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupForm() {
        return "/signup";
    }

    // 회원가입 처리
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        User user = userService.saveUser(userRequestDto);
        UserResponse userResponse = user.toResponse();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, userResponse), HttpStatus.OK);

    }
    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "";
    }

    //로그인 처리
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginForm loginForm, HttpServletRequest request) {
        String getEmail = loginForm.getEmail();
        String getPassword = loginForm.getPassword();

        User loginUser = userService.login(getEmail, getPassword);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.LOGIN_USER, loginUser);

        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, loginUser),HttpStatus.OK);

    }

    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request) {
        // getSession(true) 를 사용하면 처음 들어온 사용자도 세션이 만들어지기 때문에 false로 받음
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConstant.LOGIN_USER);
        if (session == null) {
            return ResponseEntity.badRequest().body("세션 값이 존재하지 않습니다.");
        }
        // 세션에 회원 데이터가 없음
        if (loginUser == null) {
            return ResponseEntity.badRequest().body("회원 데이터가 존재하지 않습니다.");
        }
        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, loginUser.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "";
    }
}

