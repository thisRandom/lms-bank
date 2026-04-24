package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.LoginDto;
import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.vo.CurrentVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT user.id,user.username, user.real_name, user.role_id,role.role_code role FROM `sys_user` user,`sys_role` role where username=#{username} and password=#{password} and user.role_id=role.id")
    public LoginVo login(LoginDto loginDto);
    @Update("UPDATE `sys_user` SET  password=#{password}, WHERE id=#{id}")
    public void updateUser(User user);
    @Select("SELECT user.id,user.username, user.real_name, user.role_id,role.role_code role,user.status,user.phone FROM `sys_user` user,`sys_role` role where username=#{username} and password=#{password} and user.role_id=role.id")
    public CurrentVo current(LoginVo loginVo);

    /**
     * 用于查全部
     * */
    @Select("select *from `sys_user`where id=#{id}")
    public User idToUser(LoginVo loginVo);
}
