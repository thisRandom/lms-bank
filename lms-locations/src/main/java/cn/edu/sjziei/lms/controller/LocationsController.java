package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.dto.ReportLocationDto;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.LocationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 位置模块
 * */
@RestController
@RequestMapping("/locations")
public class LocationsController {
    @Autowired
    LocationsService locationsService;

    /**
     * 上报位置
     * */
    @PostMapping
    @RequiresPermissions({"DRIVER"})
    public Result reportLocation(@Valid @RequestBody ReportLocationDto dto, @RequestHeader("Authorization") String token){
        return locationsService.reportLocation(dto, token);
    }
}