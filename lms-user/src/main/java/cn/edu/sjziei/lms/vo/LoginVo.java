package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    Integer id;
    String username; //用户名
    String realName; //真实姓名
    String role; //角色
    Long roleId; //角色ID
}
