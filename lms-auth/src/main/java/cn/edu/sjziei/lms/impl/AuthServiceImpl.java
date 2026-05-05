package cn.edu.sjziei.lms.impl;

import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.util.RedisUtil;
import cn.edu.sjziei.lms.dto.LoginDto;
import cn.edu.sjziei.lms.dto.PasswordDto;
import cn.edu.sjziei.lms.mapper.AuthMapper;
import cn.edu.sjziei.lms.AuthService;
import cn.edu.sjziei.lms.util.LoginUtil;
import cn.edu.sjziei.lms.util.PasswordUtil;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.edu.sjziei.lms.vo.CurrentVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    LoginUtil loginUtil;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    PasswordUtil passwordUtil;

    @Override
    public Result login(LoginDto loginDto) {
        //验证码
        /*String capKey ="CAPTCHA:" +loginDto.getCode().toLowerCase();
        String uuid = (String)redisUtil.get(capKey);
        if (uuid==null||!StrUtil.equals(uuid,loginDto.getUuid())) {
            return Result.error(400, "验证码错误");
        }*/

        String capKey ="CAPTCHA:" +loginDto.getCode().toLowerCase();
        if(!StrUtil.equals(capKey,"11111")){
            return Result.error(400,"验证码错误");
        }

        //删除验证码
        redisUtil.del(capKey);

        try {
            loginDto.setPassword(loginUtil.ePToPassword(loginDto.getPassword()));
        } catch (Exception e) {
            return Result.error(400, "用户名或密码错误");
        }

        //验证密码
        String hashpw = authMapper.UnToPw(loginDto.getUsername());
        if (hashpw == null || !passwordUtil.verificationPassword(loginDto.getPassword(), hashpw)) {
            return Result.error(400, "用户名或密码错误");
        }

        //验证状态
        Integer status=authMapper.veriStatus(loginDto.getUsername());
        if(Objects.equals(0,status)){
            return Result.error(400,"用户已被禁用");
        }

        //得到数据
        loginDto.setPassword(hashpw);
        LoginVo loginVo = authMapper.login(loginDto);

        //单点登录
        String key = "auth:" + loginVo.getId();
        String value = UUID.randomUUID().toString();
        redisUtil.set(key, value, 60 * 60 * 2);

        //获取token
        long timeMillis = System.currentTimeMillis() + 1000 * 60 * 60 * 2;
        String token = tokenUtil.getToken(timeMillis, loginVo, value);

        //返回的数据
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expireTime", timeMillis);
        map.put("user", loginVo);
        return Result.success(200, map);
    }

    @Override
    public Result logout(String token) {
        Long id = tokenUtil.analysisToken(token).getId();
        redisUtil.del("auth:" + id);
        return Result.success(200);
    }

    /**
     * 获取当前用户
     *
     */
    @Override
    public Result current(String token) {
        LoginVo loginVo = tokenUtil.analysisToken(token);
        //再去调用mapper来获取值
        CurrentVo currentVo = authMapper.current(loginVo);
        return Result.success(200, currentVo);
    }

    @Override
    public Result password(String token, PasswordDto passwordDto) {
        //后端解密旧密码
        try {
            passwordDto.setOldPassword(loginUtil.ePToPassword(passwordDto.getOldPassword()));
        } catch (Exception e) {
            return Result.error(400, "旧密码错误");
        }

        //把密码和token放到一个类里
        LoginVo loginVo = tokenUtil.analysisToken(token);
        LoginDto loginDto = new LoginDto(loginVo.getUsername(), passwordDto.getOldPassword(),null,null);
        String hashpw = authMapper.UnToPw(loginDto.getUsername());
        if (hashpw == null || !passwordUtil.verificationPassword(loginDto.getPassword(), hashpw)) {
            return Result.error(400, "旧密码错误");
        }

        //后端解密新密码
        try {
            passwordDto.setNewPassword(loginUtil.ePToPassword(passwordDto.getNewPassword()));
        } catch (Exception e) {
            return Result.error(400, "新密码不符合规范");
        }

        //验证新密码是否符合规范
        if (!passwordUtil.isPasswordValid(passwordDto.getNewPassword())) {
            return Result.error(400, "新密码不符合规范");
        }

        //验证新密码是否与旧密码相同
        if (passwordUtil.verificationPassword(passwordDto.getNewPassword(), hashpw)) {
            return Result.error(400, "新密码不能与旧密码相同");
        }

        //存入数据库
        loginDto.setPassword(passwordUtil.encryptionPassword(passwordDto.getNewPassword()));
        authMapper.updateUser(loginDto);

        //把当前的登录状态删除掉
        redisUtil.del("auth:" + loginVo.getId());

        return Result.success(200);
    }
}
