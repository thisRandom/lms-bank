package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public Result getListUsers(GetListUsersDto getListUsersDto,String token);

    Result addUser(AddUserDto addUserDto);

    Result editUser(EditUserDto editUserDto,Long id, String token);

    Result deleteUser(Long id);

    Result resetPassword(Long id);

    Result edUser(Long id, EDUserDto edUserDto);

    Result editBasicInfo(EditBasicInfoDto editBasicInfoDto, String token);
}
