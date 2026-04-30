package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.util.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 图形验证码
 * */
@RestController
public class CaptchaController {
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("/captcha")
    public Result captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 使用线性验证码（宽、高、位数）
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);

        //生成uuid,并通过Header返回
        String uuid = UUID.randomUUID().toString();
        response.setHeader("captcha-uuid", uuid);
        response.setHeader("Access-Control-Expose-Headers", "captcha-uuid");

        //存入redis
        String key = "CAPTCHA:" + captcha.text().toLowerCase();
        redisUtil.set(key, uuid, 60 * 5);

        // 设置响应头并输出图片流
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        captcha.out(response.getOutputStream());

        return Result.success(200,111);
    }
}
