package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIdVehiclesVo {
    Long id; //车辆id
    String plateNumber; //车牌号
    String vehicleType; //车辆类型
    Double loadCapacity; //载重
    String driverName; //司机姓名
    String lastLocation; //最后位置
    Long driverId; //司机id
    Double volume;//体积
}
