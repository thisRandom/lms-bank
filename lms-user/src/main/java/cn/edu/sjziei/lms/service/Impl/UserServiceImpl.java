package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.common.dto.ResetPasswordDto;
import cn.edu.sjziei.lms.common.util.RedisUtil;
import cn.edu.sjziei.lms.common.vo.ResetPasswordVo;
import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.common.util.PasswordUtil;
import cn.edu.sjziei.lms.common.util.TokenUtil;
import cn.edu.sjziei.lms.common.vo.LoginVo;
import cn.edu.sjziei.lms.dto.AddUserDto;
import cn.edu.sjziei.lms.dto.EDUserDto;
import cn.edu.sjziei.lms.dto.EditUserDto;
import cn.edu.sjziei.lms.dto.GetListUsersDto;
import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.service.UserService;
import cn.edu.sjziei.lms.util.UserUtil;
import cn.edu.sjziei.lms.vo.AddUserVo;
import cn.edu.sjziei.lms.vo.GetListUsersVo;
import cn.edu.sjziei.lms.vo.RecordVo;
import cn.edu.sjziei.lms.common.util.LoginUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserUtil userUtil;
    @Autowired
    PasswordUtil passwordUtil;
    @Autowired
    LoginUtil loginUtil;
    @Autowired
    RedisUtil redisUtil;


    public Result getListUsers(GetListUsersDto getListUsersDto, String token) {
        //解析出来是管理员还是调度员,如果是调度员true
        String role = tokenUtil.analysisToken(token).getRole();
        if (StrUtil.equals("DISPATCHER", role)) getListUsersDto.setBool(true);

        //分页
        PageHelper.startPage(getListUsersDto.getPage(), getListUsersDto.getSize());
        List<RecordVo> listUsers = userMapper.getListUsers(getListUsersDto);
        PageInfo<RecordVo> info = new PageInfo<>(listUsers);
        return Result.success(200, new GetListUsersVo(info.getTotal(), info.getPages(), info.getPageNum(), listUsers));
    }

    @Override
    public Result addUser(AddUserDto addUserDto) {
        try {
            addUserDto.setPassword(loginUtil.ePToPassword(addUserDto.getPassword()));
        } catch (Exception e) {
            return Result.error(400, "密码不符合规范");
        }
        if (addUserDto.getUsername() == null || !userUtil.inspectionUn(addUserDto.getUsername()))
            return Result.error(400, "用户名不符合要求");
        if (addUserDto.getPhone() == null || !userUtil.inspectionP(addUserDto.getPhone()))
            return Result.error(400, "手机号不符合要求");
        if (addUserDto.getPassword() == null || !passwordUtil.isPasswordValid(addUserDto.getPassword()))
            return Result.error(400, "密码不符合要求");
        if (addUserDto.getRealName() == null)
            return Result.error(400, "真实姓名不符合要求");
        if (addUserDto.getRoleId() == null || addUserDto.getRoleId() < 1 || addUserDto.getRoleId() > 4)
            return Result.error(400, "角色ID不符合要求");

        //验证用户名是否为唯一的
        if (userMapper.onlyUsername(addUserDto.getUsername()) > 0) {
            return Result.error(400, "用户名重复");
        }
        //密码转成加密的存储进去
        addUserDto.setPassword(passwordUtil.encryptionPassword(addUserDto.getPassword()));
        userMapper.addUser(addUserDto);
        //再通过用户名去查id
        AddUserVo addUserVo = new AddUserVo(userMapper.unToId(addUserDto.getUsername()));
        return Result.success(200, addUserVo);
    }

    @Override
    public Result editUser(EditUserDto editUserDto, Integer id, String token) {
        editUserDto.setId(id);
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        //可编辑所有用户的所有字段
        if (StrUtil.equals("ADMIN", role)) {
//            try {
//                editUserDto.setPassword(loginUtil.ePToPassword(editUserDto.getPassword()));
//            } catch (Exception e) {
//                return Result.error(400, "密码不符合规范");
//            }
//            if (editUserDto.getPassword() == null || !passwordUtil.isPasswordValid(editUserDto.getPassword()))
//                return Result.error(400, "密码不符合要求");
            if (!userUtil.inspectionP(editUserDto.getPhone()))
                return Result.error(400, "手机号不符合要求");
            if (editUserDto.getRoleId() == null || editUserDto.getRoleId() < 1 || editUserDto.getRoleId() > 4)
                return Result.error(400, "角色ID不符合要求");
            if (editUserDto.getStatus() != 1 && editUserDto.getStatus() != 0) {
                return Result.error(400, "状态不符合要求");
            }
//            editUserDto.setPassword(passwordUtil.encryptionPassword(editUserDto.getPassword()));
            //存入数据库
            userMapper.editUserToAdmin(editUserDto);
        }
        //可编辑 DRIVER 角色的 phone, realName
        else if (StrUtil.equals("DISPATCHER", role)) {
            //先判断传回来的id是否为司机的
            if (!StrUtil.equals("DRIVER", userMapper.idToRole(id))) return Result.error(400, "没有权限");
            //在实现其他业务逻辑
//            try {
//                editUserDto.setPassword(loginUtil.ePToPassword(editUserDto.getPassword()));
//            } catch (Exception e) {
//                return Result.error(400, "密码不符合规范");
//            }
//            if (editUserDto.getPassword() == null || !passwordUtil.isPasswordValid(editUserDto.getPassword()))
//                return Result.error(400, "密码不符合要求");
            if (!userUtil.inspectionP(editUserDto.getPhone()))
                return Result.error(400, "手机号不符合要求");
            //editUserDto.setPassword(passwordUtil.encryptionPassword(editUserDto.getPassword()));
            //存入数据库
            userMapper.editUserToDispatcher(editUserDto);
        }
        //只能编辑自己的 phone, realName
        else {
            //先判断是不是自己的
            if (!Objects.equals(loginVo.getId(), id)) {
                return Result.error(400, "没有权限编辑");
            }
            if (!userUtil.inspectionP(editUserDto.getPhone()))
                return Result.error(400, "手机号不符合要求");
            //存入数据库
            userMapper.editUserToOther(editUserDto);
        }
        return Result.success(200);
    }

    @Override
    public Result deleteUser(Integer id) {
        userMapper.deleteUser(id);
        return Result.success(200);
    }

    @Override
    public Result resetPassword(Integer id) {
        //默认密码“123456Abc”
        String password="123456Abc";
        userMapper.resetPassword(new ResetPasswordDto(id,passwordUtil.encryptionPassword(password)));
        //返回给前端的密文
        loginUtil.PToeP(password);
        return Result.success(200,new ResetPasswordVo(password));
    }

    @Override
    public Result edUser(Integer id, EDUserDto edUserDto) {
        edUserDto.setId(id);
        userMapper.edUser(edUserDto);
        if(Objects.equals(0,edUserDto.getStatus())){
            redisUtil.del("auth:" + id);
        }
        return Result.success(200);
    }
}
