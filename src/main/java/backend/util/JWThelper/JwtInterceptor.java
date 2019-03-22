package backend.util.JWThelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class JwtInterceptor implements HandlerInterceptor {

    private final static Logger logger =
            LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("utf-8");
        String requestURI = httpServletRequest.getRequestURI();
        String param_token = httpServletRequest.getParameter("token");
        String header_token = httpServletRequest.getHeader("token");
        if(header_token == null && param_token == null){

//            if(requestURI.contains("/user/login")) {
//                System.out.println(requestURI);
//                return true;
//            }

            String str="{'result':'fail','message':'缺少token，无法验证','data':null}";
            dealErrorReturn(httpServletRequest,httpServletResponse,str);
            return false;
        }
        if(param_token!=null){
            header_token=param_token;
        }

//        header_token =
        jwtUtil.verify(header_token) ;

        httpServletResponse.setHeader("token",header_token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    // 检测到没有token，直接返回不验证
    public void dealErrorReturn(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object obj){
        String json = (String)obj;
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);
        } catch (IOException ex) {
            logger.error("response error",ex);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
