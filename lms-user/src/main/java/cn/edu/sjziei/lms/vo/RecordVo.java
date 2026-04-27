package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordVo {
    Integer id; //编号
    String username; //用户名
    String realName; //真实姓名
    String phone; //手机号
    Long roleId; //角色Id
    String roleName; //角色名称
    Integer status; //状态
    String createTime; //创建时间
}
