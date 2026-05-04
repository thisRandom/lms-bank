package cn.edu.sjziei.lms.service.impl;

import cn.edu.sjziei.lms.dto.ReportLocationDto;
import cn.edu.sjziei.lms.mapper.LocationsMapper;
import cn.edu.sjziei.lms.result.Result;
import cn.edu.sjziei.lms.service.LocationsService;
import cn.edu.sjziei.lms.util.TokenUtil;
import cn.edu.sjziei.lms.vo.DispatchToIdVo;
import cn.edu.sjziei.lms.vo.LocationTrackVo;
import cn.edu.sjziei.lms.vo.LoginVo;
import cn.edu.sjziei.lms.vo.ReportLocationVo;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationsServiceImpl implements LocationsService {
    @Autowired
    LocationsMapper locationsMapper;
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public Result reportLocation(ReportLocationDto dto, String token) {
        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();

        Long driverId = locationsMapper.getDriverIdByDispatchId(dto.getDispatchId()).getDriverId();
        if(driverId == null){
            return Result.error(404, "调度不存在");
        }
        if(!driverId.equals(loginVo.getId())){
            return Result.error(403, "只能上报自己调度的位置");
        }

        if(dto.getRecordTime() == null){
            dto.setRecordTime(new Date());
        }
        //先找到车辆id
        Long VehId=locationsMapper.selectVehId(dto.getDispatchId());
        //更新车辆表最后位置
        locationsMapper.updateVehLastLocation(VehId,dto.getLocation());
        //更新调度的当前位置
        locationsMapper.updateDiscurrentLocation(dto.getDispatchId(),dto.getLocation());

        locationsMapper.reportLocation(dto);
        return Result.success(200, new ReportLocationVo(dto.getId()));
    }

    @Override
    public Result getDispatchLocations(Long dispatchId, String token) {
        //看状态
        String status = locationsMapper.getOrderStatusById(dispatchId);
        if ("ARRIVED".equals(status) || "SIGNED".equals(status) ) {
            return Result.success(400,"此订单不可在上传轨迹");
        }

        LoginVo loginVo = tokenUtil.analysisToken(token);
        String role = loginVo.getRole();

        DispatchToIdVo driverIdByDispatchId = locationsMapper.getDriverIdByDispatchId(dispatchId);
        Long driverId = driverIdByDispatchId.getDriverId();
        if(driverId == null){
            return Result.error(404, "调度不存在");
        }

        // DRIVER只能查询自己调度的轨迹
        if(StrUtil.equals("DRIVER", role) && !driverId.equals(loginVo.getId())){
            return Result.error(403, "无权限查询此调度轨迹");
        }
        if(StrUtil.equals("CUSTOMER",role)){
            //调度id查订单id之后查询出来客户id
            Long customerId = locationsMapper.getOrderToUserId(driverIdByDispatchId.getOrderId());
            if(!customerId.equals(loginVo.getId())) return Result.error(403, "无权限查询此调度轨迹");
        }

        List<LocationTrackVo> locations = locationsMapper.getLocationsByDispatchId(dispatchId);
        return Result.success(200, locations);
    }
}