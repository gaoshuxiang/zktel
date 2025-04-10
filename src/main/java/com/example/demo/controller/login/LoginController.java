package com.example.demo.controller.login;

import com.example.demo.entity.request.UserRequest;
import com.example.demo.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;


    /*验证用户账号是否合法*/
    @PostMapping(value = "/getUserInfo")
    public UserRequest getUserInfo(@RequestBody UserRequest userRequest) {
        return loginService.getUserInfo(userRequest);
    }
}
