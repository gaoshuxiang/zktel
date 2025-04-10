package com.example.demo.common;

import com.alibaba.excel.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.entity.pojo.Consumer;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    /*过滤器*/

    @Autowired
    private UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, final Object handler) {
        String token = request.getHeader("token");  //header里面传过来的参数
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");  //url参数  ?token= xxxxxx
        }

        //执行认证
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }

        //获取token中的user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ServiceException("401", "请登录,获取token中ID失败");
        }

        //根据token中的userId查询数据库
        Consumer consumer = userMapper.selectById(userId);
        if (consumer == null) {
            throw new ServiceException("401", "请登录,根据ID查询数据库User为空");
        }

        //通过用户密码加密之后生成一个验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(consumer.getPwd())).build();
        try {
            jwtVerifier.verify(token);//验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录,验证token失败");
        }
        return true;
    }
}
