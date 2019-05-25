package backend.util.config;

import backend.util.JWThelper.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    /**
     * 为了测试方便，暂时注释掉拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
//         可添加多个
        registry.addInterceptor(getJwtHeader())
                .excludePathPatterns("/api/user/login")
//                .excludePathPatterns("/user/login")
                .excludePathPatterns("/")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/api/scenario/getSectionsAndComponents")
                .excludePathPatterns("/api/experiment")
//                .excludePathPatterns("/static/css/index.css")
//                .excludePathPatterns("/static/js/index.js")
//                .excludePathPatterns("/static/js/1.async.js")
//                .excludePathPatterns("/static/js/0.async.js")
//                .excludePathPatterns("/static/js/static/default-experiment.ebf878f9.png")
//                .excludePathPatterns("/static/js/static/index.c4014088.jpg")
                .addPathPatterns("/**");
    }

    //token 在header的拦截器

    /**
     * 添加静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    public HandlerInterceptor getJwtHeader() {
        return new JwtInterceptor();
    }
}
