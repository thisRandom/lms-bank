package cn.edu.sjziei.lms.service.Impl;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.common.util.TokenUtil;
import cn.edu.sjziei.lms.common.vo.LoginVo;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.dto.UpdateVehicleDto;
import cn.edu.sjziei.lms.entity.Vehicle;
import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.mapper.VehicleMapper;
import cn.edu.sjziei.lms.service.VehicleService;
import cn.edu.sjziei.lms.util.VehicleUtil;
import cn.edu.sjziei.lms.vo.AddVehicleVo;
import cn.edu.sjziei.lms.vo.GetIdVehiclesVo;
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
    public Result getVehicleList(GetVehicleListDto getVehicleListDto, String token) {
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();
        if (StrUtil.equals("DRIVER", role)) {
            getVehicleListDto.setDriverId(loginVo.getId());
        }
        PageHelper.startPage(getVehicleListDto.getPage(), getVehicleListDto.getSize());
        List<VehicleVo> list = vehicleMapper.getVehicleList(getVehicleListDto);
        PageInfo<VehicleVo> info = new PageInfo<>(list);
        return Result.success(200, new GetVehicleListVo(info.getTotal(), info.getPages(), info.getPageNum(), list));
    }

    @Override
    public Result addVehicle(AddVehicleDto addVehicleDto, String token) {
        if (!vehicleUtil.verPlateNumber(addVehicleDto.getPlateNumber())) return Result.error(400, "车牌号不符合规则");
        if (!vehicleUtil.verVehicleType(addVehicleDto.getVehicleType())) return Result.error(400, "车辆类型不符合规则");
        if (!vehicleUtil.verLoadCapacity(addVehicleDto.getLoadCapacity())) return Result.error(400, "吨数不符合规则");

        LoginVo loginVo = tokenUtil.analysisToken(token);

        if (!StrUtil.equals("DRIVER", userMapper.idToRole(addVehicleDto.getDriverId()))) {
            return Result.error(400, "该用户不能使用该方法");
        }
        vehicleMapper.addVehicle(addVehicleDto);
        Long id = vehicleMapper.plateNumberToId(addVehicleDto.getPlateNumber());
        return Result.success(200, new AddVehicleVo(id));
    }

    @Override
    public Result updateVehicle(Long id, UpdateVehicleDto dto) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        if (vehicle == null) {
            return Result.error(404, "车辆不存在");
        }

        if (dto.getVehicleType() != null) vehicle.setVehicleType(dto.getVehicleType());
        if (dto.getLoadCapacity() != null) vehicle.setLoadCapacity(dto.getLoadCapacity());
        if (dto.getDriverId() != null) vehicle.setDriverId(dto.getDriverId());
        if (dto.getStatus() != null) vehicle.setStatus(dto.getStatus());

        vehicleMapper.update(vehicle);
        return Result.success(200, null);
    }

    @Override
    public Result deleteVehicle(Long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        if (vehicle == null) {
            return Result.error(404, "车辆不存在");
        }
        vehicleMapper.deleteById(id);
        return Result.success(200, null);
    }

    @Override
    public Result getIdleVehicles() {
        List<GetIdVehiclesVo> list = vehicleMapper.selectIdleVehicles();
        return Result.success(200, list);
    }
}
