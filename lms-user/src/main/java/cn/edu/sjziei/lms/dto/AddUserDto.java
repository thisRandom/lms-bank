package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
    String username; //用户名（4-20位）
    String password; //密码（6-20位）
    String realName; //真实姓名
    String phone; //手机号
    Long roleId; //角色ID
    Integer status=1; //状态
}
