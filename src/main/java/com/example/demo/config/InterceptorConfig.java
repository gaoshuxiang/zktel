package com.example.demo.config;

import com.example.demo.common.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    /*JWT配置类*/

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwt())
                .addPathPatterns("/**")/*需要token验证的路径*/
                .excludePathPatterns("/login/getUserInfo");
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwt() {
        return new JwtInterceptor();
    }
}
