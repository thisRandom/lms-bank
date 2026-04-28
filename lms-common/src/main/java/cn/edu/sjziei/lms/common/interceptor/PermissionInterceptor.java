package cn.edu.sjziei.lms.common.interceptor;

import cn.edu.sjziei.lms.common.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.common.util.TokenUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取路径
        String requestUri = request.getRequestURI();
        //如果路径是白名单的就放行
        if (this.isExcludedPath(requestUri)) {
            return true;
        }

        //获取token
        String token = request.getHeader("Authorization");
        //验证token合法性
        try {
            if (token == null || !tokenUtil.verificationToken(token)) {
                return this.returnErrorJson(response, 401, "用户登录过期");
            }
        } catch (Exception e) {
            return this.returnErrorJson(response, 401, "用户登录过期");
        }

        //单点登录
        if (!tokenUtil.tokenValueEqualToRedis(token)) {
            return this.returnErrorJson(response, 401, "该账号已在别的设备登录");
        }

        //鉴权
        if (!(handler instanceof HandlerMethod)) {
            // 如果是 ResourceHttpRequestHandler (静态资源)
            // 或者其他的处理器，直接放行，不进行权限校验
            return true;
        }
        // 将 handler 强转为 HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取方法上的 RequiresPermissions 注解
        RequiresPermissions annotation = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
        //这里面是需要鉴权的接口
        if (annotation != null) {
            // 拿到注解里定义的权限字符串数组
            String[] requiredPermissions = annotation.value();
            //获取token里的LoginVo
            String role=tokenUtil.analysisToken(token).getRole();
            for (String requiredPermission : requiredPermissions) {
                if(StrUtil.equals(requiredPermission,role)){
                    return true;
                }
            }
            return this.returnErrorJson(response, 404, "找不到页面");
        }
        return true;
    }

    /**
     * 通用的返回错误方法
     */
    private boolean returnErrorJson(HttpServletResponse response, Integer code, String msg) throws Exception {
        // 设置响应头
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //Result转成字符串传递错误信息
        String json = MAPPER.writeValueAsString(Result.error(code, msg));

        response.getWriter().write(json);
        return false; // 拦截请求
    }

    /**
     * 看是否为白名单的接口
     */
    private boolean isExcludedPath(String requestUri) {
        // 定义白名单路径
        String[] excludePaths = {
                "/auth/login",// 登录接口
                "/captcha"
        };

        for (String pattern : excludePaths) {
            if (StrUtil.equals(pattern, requestUri)) {
                return true;
            }
        }
        return false;
    }
}