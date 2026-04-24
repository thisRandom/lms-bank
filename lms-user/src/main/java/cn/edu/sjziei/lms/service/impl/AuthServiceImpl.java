package cn.edu.sjziei.lms.service.impl;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.common.util.RedisUtil;
import cn.edu.sjziei.lms.dto.LoginDto;
import cn.edu.sjziei.lms.dto.PasswordDto;
import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.service.AuthService;
import cn.edu.sjziei.lms.util.loginUtil;
import cn.edu.sjziei.lms.util.passwordUtil;
import cn.edu.sjziei.lms.util.tokenUtil;
import cn.edu.sjziei.lms.vo.CurrentVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    loginUtil loginUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    tokenUtil tokenUtil;
    @Autowired
    passwordUtil passwordUtil;

    @Override
    public Result login(LoginDto loginDto) {
        try {
            loginDto.setPassword(loginUtil.ePToPassword(loginDto.getPassword()));
        } catch (Exception e) {
            return Result.error(500, "用户名或密码错误");
        }

        //验证密码(把权限等等等赋值到这里即可，不要有那个密码就行)
        LoginVo loginVo = userMapper.login(loginDto);
        if (loginVo == null) {
            return Result.error(500, "用户名或密码错误");
        }

        //单点登录
        String key = "auth:" + loginVo.getId();
        String value = UUID.randomUUID().toString();
        redisUtil.set(key,value,60*60*2 );

        //获取token
        long timeMillis = System.currentTimeMillis()+ + 1000 * 60 * 60 * 2;
        String token=tokenUtil.getToken(timeMillis,loginVo);

        //返回的数据
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("expireTime",timeMillis);
        map.put("user",loginVo);
        return Result.success(200, map);
    }

    @Override
    public Result logout(String token) {
        Integer id=tokenUtil.analysisToken(token).getId();
        redisUtil.del("auth:"+id);
        return Result.success(200);
    }

    /**
     * 获取当前用户
     * */
    @Override
    public Result current(String token) {
        LoginVo loginVo=tokenUtil.analysisToken(token);
        //再去调用mapper来获取值
        CurrentVo currentVo=userMapper.current(loginVo);
        return Result.success(200,currentVo);
    }

    @Override
    public Result password(String token, PasswordDto passwordDto) {
        //后端解密旧密码
        try {
            passwordDto.setOldPassword(loginUtil.ePToPassword(passwordDto.getOldPassword()));
        } catch (Exception e) {
            return Result.error(500, "旧密码错误");
        }

        //把密码和token放到一个类里
        LoginVo loginVo = tokenUtil.analysisToken(token);
        LoginDto loginDto=new LoginDto(loginVo.getUsername(),passwordDto.getOldPassword());
        if (userMapper.login(loginDto) == null) {
            return Result.error(500, "旧密码错误");
        }

        //后端解密新密码
        try {
            passwordDto.setNewPassword(loginUtil.ePToPassword(passwordDto.getNewPassword()));
        } catch (Exception e) {
            return Result.error(500, "新密码不符合规范");
        }

        //验证新密码是否符合规范
        if (!passwordUtil.isPasswordValid(passwordDto.getNewPassword())) {
            return Result.error(500,"新密码不符合规范");
        }

        //存入数据库
        User user=userMapper.idToUser(loginVo);
        user.setPassword(passwordDto.getNewPassword());
        userMapper.updateUser(user);

        return Result.success(200);
    }
}
