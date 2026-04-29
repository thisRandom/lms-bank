package cn.edu.sjziei.lms.util;

import cn.edu.sjziei.lms.mapper.VehicleMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆工具
 * */
@Component
public class VehicleUtil {
    @Autowired
    VehicleMapper vehicleMapper;

    /**
     * 验证车牌是否合法
     * */
    public boolean verPlateNumber(String plateNumber){
        if (plateNumber==null) return false;
        String regex="^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳上试]))$";
        if (!plateNumber.matches(regex)) return false;
        //验证车牌是否唯一
        if (vehicleMapper.countByPlateNumber(plateNumber)>0) return false;
        return true;
    }

    /**
     * 验证车辆类型是否合法
     * */
    public boolean verVehicleType(String vehicleType){
        if (vehicleType==null) return false;
        return switch (vehicleType) {
            case "TRUCK", "VAN", "PICKUP" -> true;
            default -> false;
        };
    }
    /**
     * 验证吨数是否合法
     * */
    public boolean verLoadCapacity(Double loadCapacity){
        return loadCapacity != null && (loadCapacity > 0.0);
    }
}
