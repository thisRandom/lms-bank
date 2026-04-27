package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.common.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import cn.edu.sjziei.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块
 * */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 获取用户列表
     * */
    @GetMapping
    @RequiresPermissions({"ADMIN","DISPATCHER"})
    public Result getListUsers(GetListUsersDto getListUsersDto,@RequestHeader("Authorization") String token){
        return userService.getListUsers(getListUsersDto,token);
    }

    /**
     * 新增用户
     * */
    @PostMapping
    @RequiresPermissions({"ADMIN"})
    public Result addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }


}
