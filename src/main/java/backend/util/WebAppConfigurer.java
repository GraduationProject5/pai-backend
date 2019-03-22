package backend.util;

import backend.util.jwthelper.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(getJwtHeader()).addPathPatterns("/**");
    }

    //token 在header的拦截器

    public HandlerInterceptor getJwtHeader(){
        return new JwtInterceptor();
    }
}
