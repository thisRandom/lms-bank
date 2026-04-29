package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVehicleListDto {
    Long driverId; //司机id
    Integer page=1; //页码，默认1
    Integer size=10; //每页条数，默认10
    String keyword; //搜索关键字（车牌号/司机）
    String status; //状态
    String vehicleType; //车辆类型
}
