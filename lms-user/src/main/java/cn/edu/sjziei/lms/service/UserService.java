package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public Result getListUsers(GetListUsersDto getListUsersDto,String token);

    Result addUser(AddUserDto addUserDto);

    Result editUser(EditUserDto editUserDto,Integer id, String token);

    Result deleteUser(Integer id);

    Result resetPassword(Integer id);

    Result edUser(Integer id, EDUserDto edUserDto);

    Result editBasicInfo(EditBasicInfoDto editBasicInfoDto, String token);
}
