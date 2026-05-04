package cn.edu.sjziei.lms.controller;

import cn.edu.sjziei.lms.annotation.RequiresPermissions;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 可视化部分
 * */
@RestController
@RequestMapping("/dispatches")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    /**
     * 数据概览统计
     * */
    @GetMapping("/stats")
    @RequiresPermissions({"ADMIN","DISPATCHER"})
    public Result getDashboard(){
        return dashboardService.getDashboard();
    }
}
