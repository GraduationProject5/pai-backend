package backend.util.config;

import backend.util.JWThelper.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    /**
     * 为了测试方便，暂时注释掉拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
//        registry.addInterceptor(getJwtHeader())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/login");
    }

    //token 在header的拦截器

    public HandlerInterceptor getJwtHeader(){
        return new JwtInterceptor();
    }
}
