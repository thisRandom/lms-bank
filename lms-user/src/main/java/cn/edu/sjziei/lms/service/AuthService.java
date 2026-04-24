package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.LoginDto;
import cn.edu.sjziei.lms.dto.PasswordDto;
import cn.edu.sjziei.lms.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public Result login(LoginDto loginDto);

    public Result logout(String token);

    public Result current(String token);

    public Result password(String token, PasswordDto passwordDto);
}
