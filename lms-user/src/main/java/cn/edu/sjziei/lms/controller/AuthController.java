package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.LoginDto;
import cn.edu.sjziei.lms.dto.PasswordDto;
import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.service.AuthService;
import cn.edu.sjziei.lms.util.tokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口
 * */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    private tokenUtil tokenUtil;

    /**
     * 登录接口
     * */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }


    /**
     * 登出接口
     * */
    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return authService.logout(token);
    }

    /**
    * 获取当前用户
    * */
    @GetMapping("/current")
    public Result current(@RequestHeader("Authorization") String token){
        return authService.current(token);
    }

    /**
     * 修改密码
     * */
    @PutMapping("/password")
    public Result password(@RequestBody PasswordDto passwordDto, @RequestHeader("Authorization") String token){
        return authService.password(token,passwordDto);
    }
}
