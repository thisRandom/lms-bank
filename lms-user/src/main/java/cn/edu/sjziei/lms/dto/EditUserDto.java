package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {
    Integer id;
    @NotNull(message = "真实姓名不能为空")
    String realName; //真实姓名
    @NotNull(message = "手机号不能为空")
    String phone; //手机号
    //String password; //密码（6-20位）
    Long roleId; //角色ID
    //Integer status; //状态
}
