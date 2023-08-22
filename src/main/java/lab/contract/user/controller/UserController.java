
package lab.contract.user.controller;

import lab.contract.user.persistence.User;
import lab.contract.user.service.UserService;
import lab.contract.user.session.SessionConstant;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "";
    }



}