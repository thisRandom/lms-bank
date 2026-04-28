package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.common.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.EDUserDto;
import cn.edu.sjziei.lms.dto.EditUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import cn.edu.sjziei.lms.service.UserService;
import jakarta.validation.Valid;
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

    /**
     * 编辑用户
     * */
    @PutMapping("/{id}")
    public Result editUser(@Valid @RequestBody EditUserDto editUserDto, @PathVariable("id") Integer id, @RequestHeader("Authorization") String token){
        return userService.editUser(editUserDto,id,token);
    }

    /**
     * 删除用户
     * */
    @DeleteMapping("/{id}")
    @RequiresPermissions({"ADMIN"})
    public Result deleteUser(@PathVariable("id") Integer id){
        return userService.deleteUser(id);
    }

    /**
     * 重置密码
     * */
    @PutMapping("/{id}/reset-password")
    @RequiresPermissions({"ADMIN"})
    public Result resetPassword(@PathVariable("id") Integer id){
        return userService.resetPassword(id);
    }

    /**
     *启用禁用用户
     * */
    @PutMapping("/{id}/status")
    @RequiresPermissions({"ADMIN"})
    public Result eDUser(@Valid @RequestBody EDUserDto edUserDto, @PathVariable("id") Integer id){
        return userService.edUser(id,edUserDto);
    }
}
