package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.common.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.common.result.Result;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 车辆模块
 * */
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    /**
     * 获取车辆列表
     * */
    @GetMapping
    @RequiresPermissions({"ADMIN","DISPATCHER"})
    public Result getVehicleList(GetVehicleListDto getVehicleListDto){
        return vehicleService.getVehicleList(getVehicleListDto);
    }

    /**
     * 新增车辆
     * */
    @PostMapping
    @RequiresPermissions({"ADMIN","DRIVER"})
    public Result addVehicle(@RequestBody AddVehicleDto addVehicleDto,@RequestHeader("Authorization") String token){
        return vehicleService.addVehicle(addVehicleDto,token);
    }
}
