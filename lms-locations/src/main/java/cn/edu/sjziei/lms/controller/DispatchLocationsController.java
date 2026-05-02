package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 调度轨迹查询
 * */
@RestController
@RequestMapping("/dispatches")
public class DispatchLocationsController {
    @Autowired
    LocationsService locationsService;

    @GetMapping("/{dispatchId}/locations")
    @RequiresPermissions({"DISPATCHER","DRIVER","CUSTOMER"})
    public Result getDispatchLocations(@PathVariable Long dispatchId, @RequestHeader("Authorization") String token){
        return locationsService.getDispatchLocations(dispatchId, token);
    }
}