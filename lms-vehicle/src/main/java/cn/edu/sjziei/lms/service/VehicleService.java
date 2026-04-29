package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.dto.UpdateVehicleDto;
import cn.edu.sjziei.lms.vo.VehicleVo;
import java.util.List;

public interface VehicleService {
    Result getVehicleList(GetVehicleListDto getVehicleListDto,String token);
    Result addVehicle(AddVehicleDto addVehicleDto, String token);
    Result updateVehicle(Long id, UpdateVehicleDto dto);
    Result deleteVehicle(Long id);
    Result getIdleVehicles();
}
