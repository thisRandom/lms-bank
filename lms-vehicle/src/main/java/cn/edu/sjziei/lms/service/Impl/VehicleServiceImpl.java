package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.common.util.TokenUtil;
import cn.edu.sjziei.lms.common.vo.LoginVo;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.mapper.VehicleMapper;
import cn.edu.sjziei.lms.service.VehicleService;
import cn.edu.sjziei.lms.util.VehicleUtil;
import cn.edu.sjziei.lms.vo.AddVehicleVo;
import cn.edu.sjziei.lms.vo.GetVehicleListVo;
import cn.edu.sjziei.lms.vo.VehicleVo;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    VehicleUtil vehicleUtil;
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserMapper userMapper;

    @Override
    public Result getVehicleList(GetVehicleListDto getVehicleListDto) {
        PageHelper.startPage(getVehicleListDto.getPage(),getVehicleListDto.getSize());
        List<VehicleVo> list = vehicleMapper.getVehicleList(getVehicleListDto);
        PageInfo<VehicleVo> info=new PageInfo<>(list);
        return Result.success(200,new GetVehicleListVo(info.getTotal(),info.getPages(),info.getPageNum(),list));
    }

    @Override
    public Result addVehicle(AddVehicleDto addVehicleDto,String token) {
        if(!vehicleUtil.verPlateNumber(addVehicleDto.getPlateNumber())) return Result.error(400,"车牌号不符合规则");
        if(!vehicleUtil.verVehicleType(addVehicleDto.getVehicleType())) return Result.error(400,"车辆类型不符合规则");
        if(!vehicleUtil.verLoadCapacity(addVehicleDto.getLoadCapacity())) return Result.error(400,"吨数不符合规则");

        //看来请求的是管理员还是司机，如果是司机把id存进去之后调用mapper，如果是管理员的话，先检查传过来的id是否为司机的，如果不是司机的就返回，是的话在存储
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        if(StrUtil.equals(role,"ADMIN")){
            //查司机id是否为司机
            if (!StrUtil.equals("DRIVER",userMapper.idToRole(addVehicleDto.getDriverId()))) {
                return Result.error(400,"该用户不能使用该方法");
            }
        }
        //司机的逻辑
        else{
            addVehicleDto.setDriverId(loginVo.getId());
        }
        //存入数据库
        vehicleMapper.addVehicle(addVehicleDto);
        //通过车牌查询id
        Long id = vehicleMapper.plateNumberToId(addVehicleDto.getPlateNumber());
        return Result.success(200,new AddVehicleVo(id));
    }
}
