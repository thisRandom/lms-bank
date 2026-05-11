package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.ResetPasswordDto;
import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.EDUserDto;
import cn.edu.sjziei.lms.dto.EditUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import cn.edu.sjziei.lms.vo.RecordVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("<script>" +
            "SELECT u.id, u.username, u.real_name, u.phone, u.role_id, r.role_name, u.status, u.create_time " +
            "FROM sys_user u " +
            "LEFT JOIN sys_role r ON u.role_id = r.id " + // 改为左连接
            "<where> " +
            "  <if test='realName != null and realName != \"\" '> " +
            "    AND u.real_name LIKE CONCAT('%', #{realName}, '%') " +
            "  </if> " +
            "  <if test='roleId != null'> " +
            "    AND u.role_id = #{roleId} " +
            "  </if> " +
            "  <if test='status != null'> " +
            "    AND u.status = #{status} " +
            "  </if> " +
            "  <if test='bool == true'> " +
            "    AND r.role_code = \"DRIVER\" " +
            "  </if> " +
            "</where> " +
            "ORDER BY u.create_time DESC" +
            "</script>")
    public List<RecordVo> getListUsers(GetListUsersDto getListUsersDto);

    @Select("SELECT COUNT(*) FROM sys_user WHERE username =#{username}")
    public Integer onlyUsername(String username);

    @Insert("INSERT INTO sys_user(username, password, real_name, phone, role_id, status, create_time, update_time) " +
            "VALUES(#{username}, #{password}, #{realName}, #{phone}, #{roleId}, #{status}, NOW(), NOW())")
    public void addUser(AddUserDto addUserDto);
    @Select("SELECT id FROM sys_user WHERE username =#{username}")
    public Long unToId(String username);

    @Update("UPDATE sys_user SET real_name = #{realName}, phone = #{phone}, " +
            " role_id = #{roleId}, " +
            "update_time = NOW() WHERE id = #{id}")
    public void editUserToAdmin(EditUserDto editUserDto);

    @Select("SELECT r.role_code from sys_user u,sys_role r where u.id=#{id} and u.role_id=r.id")
    public String idToRole(Long id);

    @Update("UPDATE sys_user SET real_name = #{realName}, phone = #{phone}, " +
            "update_time = NOW() WHERE id = #{id}")
    public void editUserToDispatcher(EditUserDto editUserDto);

    @Update("UPDATE sys_user SET real_name = #{realName}, phone = #{phone}, " +
            "update_time = NOW() WHERE id = #{id}")
    public void editUserToOther(EditUserDto editUserDto);

    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    public void deleteUser(Long id);

    @Update("UPDATE sys_user SET password = #{password},update_time = NOW() where id=#{id}")
    public void resetPassword(ResetPasswordDto resetPasswordDto);

    @Update("UPDATE sys_user SET status = #{status},update_time = NOW() where id=#{id}")
    public void edUser(EDUserDto edUserDto);

    @Update("UPDATE sys_user SET headshot = #{url},update_time = NOW() where id=#{id}")
    public void resetHeadshot(@Param("id") Long id,@Param("url") String url);
}
