package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.common.dto.LoginDto;
import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.common.vo.CurrentVo;
import cn.edu.sjziei.lms.common.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT user.id,user.username, user.real_name, user.role_id,role.role_code role FROM `sys_user` user,`sys_role` role where username=#{username} and password=#{password} and user.role_id=role.id")
    public LoginVo login(LoginDto loginDto);
    @Update("UPDATE `sys_user` SET  password=#{password} WHERE username=#{username}")
    public void updateUser(LoginDto loginDto);
    @Select("SELECT user.id,user.username, user.real_name, user.role_id,role.role_code role,user.status,user.phone FROM `sys_user` user,`sys_role` role where username=#{username} and user.role_id=role.id")
    public CurrentVo current(LoginVo loginVo);
    @Select("SELECT password from `sys_user`where username=#{username}")
    public String UnToPw(String username);
    /**
     * 用于查看一个用户全部的信息
     * */
    @Select("select *from `sys_user`where id=#{id}")
    public User idToUser(LoginVo loginVo);
}
