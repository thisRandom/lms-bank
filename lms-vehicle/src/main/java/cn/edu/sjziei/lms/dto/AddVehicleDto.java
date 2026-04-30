package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVehicleDto {
    Long driverId; //司机id
    String plateNumber; //车牌号
    String vehicleType; //车辆类型
    Double loadCapacity; //载重（吨）
    Double volume; //体积（方）
    String status="IDLE"; //默认是空闲的
}
