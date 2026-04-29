package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleVo {
    Long id; //车辆id
    String plateNumber; //车牌号
    String vehicleType; //车辆类型
    Double loadCapacity; //载重
    Long driverId; //绑定司机ID
    String driverName; //司机姓名
    String driverPhone; //司机手机号
    String status; //车辆的状态
    String lastLocation; //最后位置
    String lastUpdateTime; //最后更新时间
}
