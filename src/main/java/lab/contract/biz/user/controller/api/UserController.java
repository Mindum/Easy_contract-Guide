package lab.contract.biz.user.controller.api;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lab.contract.biz.user.controller.dto.request.UserRequestDto;
import lab.contract.biz.user.persistence.entity.User;
import lab.contract.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "unauthorized")
    })
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRequestDto userRequestDto) {
        userService.saveUser(userRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
