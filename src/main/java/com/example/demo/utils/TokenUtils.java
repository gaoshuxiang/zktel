package com.example.demo.utils;

import com.alibaba.excel.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.pojo.Consumer;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;

    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    /*
    * 生成token
    *
    * */
    public static String createToken(String userId,String sign) {
        return JWT.create().withAudience(userId) //将userId存入到token中
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 7)) //毫秒计算 这里是7小时
                .sign(Algorithm.HMAC256(sign));//sign传的是密码,用密码来作token密钥
    }

    /*
     * 获取当前登录的用户信息
     *
     * */
    public static Consumer getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (!StringUtils.isBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.parseInt(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
