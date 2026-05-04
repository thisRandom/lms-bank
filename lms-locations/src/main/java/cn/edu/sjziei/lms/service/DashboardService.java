package cn.edu.sjziei.lms.service;

import cn.edu.sjziei.lms.vo.DashboardStatsResponseVo;
import cn.edu.sjziei.lms.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    Result getDashboard();
    DashboardStatsResponseVo getStats();
}
