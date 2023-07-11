package lab.contract.biz.user.controller.api;

import io.swagger.annotations.ApiResponses;
import lab.contract.biz.user.controller.dto.request.UserRequestDto;
import lab.contract.biz.user.controller.dto.response.UserResponse;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.service.UserService;
import lab.contract.infrastructure.exception.DefaultRes;
import lab.contract.infrastructure.exception.ResponseMessage;
import lab.contract.infrastructure.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity signup(@RequestBody @Valid UserRequestDto userRequestDto) {

        Long saveId = userService.saveUser(userRequestDto);
        userRequestDto.setId(saveId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER, userRequestDto), HttpStatus.OK);
    }

}
