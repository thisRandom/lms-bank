package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.dto.UpdateVehicleDto;

public interface VehicleService {
    Result getVehicleList(GetVehicleListDto getVehicleListDto,String token);
    Result addVehicle(AddVehicleDto addVehicleDto, String token);
    Result updateVehicle(Long id, UpdateVehicleDto dto);
    Result deleteVehicle(Long id);
    Result getIdleVehicles();
}
