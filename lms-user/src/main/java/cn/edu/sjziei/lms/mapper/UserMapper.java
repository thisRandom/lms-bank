package cn.edu.sjziei.lms.mapper;

import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import cn.edu.sjziei.lms.vo.RecordVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    public Integer unToId(String username);
}
