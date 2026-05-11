package cn.edu.sjziei.lms.config;

import cn.edu.sjziei.lms.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Value("${file.upload-path}") // 从 yml 读取 D:/uploads/
    private String uploadPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**");  // 关键：指定拦截哪些路径
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 这里的路径末尾一定要记得加 / 否则拼接可能会出问题
        String path = uploadPath.endsWith("/") ? uploadPath : uploadPath + "/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + path);
    }
}
