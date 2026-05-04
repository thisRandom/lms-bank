package cn.edu.sjziei.lms.service.impl;

import cn.edu.sjziei.lms.vo.DashboardStatsResponseVo;
import cn.edu.sjziei.lms.mapper.DashboardMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    DashboardMapper dashboardMapper;

    @Override
    public Result getDashboard() {
        return Result.success(200, getStats());
    }

    @Override
    public DashboardStatsResponseVo getStats() {
        DashboardStatsResponseVo response = new DashboardStatsResponseVo();
        response.setTotalOrders(dashboardMapper.getTotalOrders());
        response.setInProgressOrders(dashboardMapper.getInProgressOrders());
        response.setSignedOrders(dashboardMapper.getSignedOrders());
        response.setPendingOrders(dashboardMapper.getPendingOrders());
        response.setOrderStatusDistribution(dashboardMapper.getOrderStatusDistribution());
        response.setVehicleStatusDistribution(dashboardMapper.getVehicleStatusDistribution());
        response.setTotalWeight(dashboardMapper.getTotalWeight());
        response.setTotalVolume(dashboardMapper.getTotalVolume());
        response.setGoodsTypeDistribution(dashboardMapper.getGoodsTypeDistribution());
        return response;
    }
}
