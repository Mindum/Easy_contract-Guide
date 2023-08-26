package lab.contract.user.controller;

import lab.contract.infrastructure.exception.user.SessionNullException;
import lab.contract.user.persistence.User;
import lab.contract.user.service.UserService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import lab.contract.user.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionManager sessionManager;


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
    public ResponseEntity login(@RequestBody @Valid LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {


        String getEmail = loginForm.getEmail();
        String getPassword = loginForm.getPassword();

        User loginUser = userService.login(getEmail, getPassword);


        String sessionId = sessionManager.createSession(loginUser,response);
        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, "id : " + sessionId +" "+ loginUser.getUsername()),HttpStatus.OK);

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        sessionManager.expires(request);
        return "";
    }
    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request) {
        User loginUser = (User) sessionManager.getSession(request);
        if (loginUser == null) {
            new SessionNullException("SessionNullException",ResponseMessage.SESSION_NULL);
        }
        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS, loginUser.getUsername()),HttpStatus.OK);
    }

}