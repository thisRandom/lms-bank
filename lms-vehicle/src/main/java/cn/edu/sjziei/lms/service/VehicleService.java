package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {
    Result getVehicleList(GetVehicleListDto getVehicleListDto);

    Result addVehicle(AddVehicleDto addVehicleDto,String token);
}
