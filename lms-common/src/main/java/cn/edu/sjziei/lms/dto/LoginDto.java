package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    String username; //用户名
    String password; //密码
    String uuid; //uuid
    String code; //验证码
}
