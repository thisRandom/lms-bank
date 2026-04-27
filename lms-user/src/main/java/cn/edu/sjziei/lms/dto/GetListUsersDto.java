package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListUsersDto {
    Integer page = 1; //页码 默认1
    Integer size = 10; //每页条数 默认10
    String realName; //搜索关键字（姓名）
    Long roleId; //角色ID过滤
    Integer status; //状态：0禁用，1启用
    Boolean bool = false; //调度员true，管理员false
}
