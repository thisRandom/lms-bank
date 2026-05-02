package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.dto.AddVehicleDto;
import cn.edu.sjziei.lms.dto.GetVehicleListDto;
import cn.edu.sjziei.lms.dto.UpdateVehicleDto;
import cn.edu.sjziei.lms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping
    @RequiresPermissions({"ADMIN", "DISPATCHER","DRIVER"})
    public Result getVehicleList(GetVehicleListDto getVehicleListDto, @RequestHeader("Authorization") String token) {
        return vehicleService.getVehicleList(getVehicleListDto,token);
    }

    @PostMapping
    @RequiresPermissions({"ADMIN", "DISPATCHER"})
    public Result addVehicle(@RequestBody AddVehicleDto addVehicleDto, @RequestHeader("Authorization") String token) {
        return vehicleService.addVehicle(addVehicleDto, token);
    }

    @PutMapping("/{id}")
    @RequiresPermissions({"ADMIN", "DISPATCHER"})
    public Result updateVehicle(@PathVariable("id") Long id, @RequestBody UpdateVehicleDto dto) {
        return vehicleService.updateVehicle(id, dto);
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions({"ADMIN"})
    public Result deleteVehicle(@PathVariable("id") Long id) {
        return vehicleService.deleteVehicle(id);
    }

    @GetMapping("/idle")
    @RequiresPermissions({ "DISPATCHER"})
    public Result getIdleVehicles() {
        return vehicleService.getIdleVehicles();
    }
}
