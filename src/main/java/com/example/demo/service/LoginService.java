package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.pojo.Consumer;
import com.example.demo.entity.request.UserRequest;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class LoginService {
    private final UserMapper userMapper;

    /*登录密码验证*/
    public UserRequest getUserInfo(UserRequest userRequest) {
        if (userRequest == null) {
            throw new ServiceException("userRequest入参对象为空");
        }
        Consumer dbUser = userMapper.selectOne(new LambdaQueryWrapper<Consumer>()
                .eq(Consumer::getUsername, userRequest.getUsername()));

        if (!Objects.equals(dbUser.getPwd(),userRequest.getPwd())) {
            throw new ServiceException("dbUser对象为空");
        }
        String token = TokenUtils.createToken(String.valueOf(userRequest.getId()), String.valueOf(dbUser.getPwd()));
        userRequest.setToken(token);

        return userRequest;
    }
}
