package cn.edu.sjziei.lms.common.interceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取路径
        String requestUri = request.getRequestURI();
        //如果路径是白名单的就放行
        if (this.isExcludedPath(requestUri)) {
            return true;
        }

        //获取token
        String authorization = request.getHeader("Authorization");
        

        return true;
    }

    //看是否为白名单的接口
    private boolean isExcludedPath(String requestUri) {
        // 定义白名单路径
        String[] excludePaths = {
                "/auth/login",           // 登录接口
        };

        for (String pattern : excludePaths) {
            if ( StrUtil.equals(pattern,requestUri )) {
                return true;
            }
        }
        return false;
    }
}
@Configuration
class WebConfig implements WebMvcConfigurer {
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**");  // 关键：指定拦截哪些路径
    }
}