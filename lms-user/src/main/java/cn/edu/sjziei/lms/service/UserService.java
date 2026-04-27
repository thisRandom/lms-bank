package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public Result getListUsers(GetListUsersDto getListUsersDto,String token);

    Result addUser(AddUserDto addUserDto);
}
