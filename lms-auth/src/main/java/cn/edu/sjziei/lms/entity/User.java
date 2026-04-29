package cn.edu.sjziei.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    Long id;
    String username; //用户名
    String password; //密码
    String realName; //真实姓名
    String phone; //手机号
    Long roleId; //角色ID
    Integer status; //状态
    String createTime; //创建时间
    String updateTime; //更新时间
}
