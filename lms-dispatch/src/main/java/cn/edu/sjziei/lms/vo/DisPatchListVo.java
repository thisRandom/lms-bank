package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisPatchListVo {
    Long id; //调度ID
    String dispatchNo; //调度单号
    String orderNo; //订单号
    String plateNumber; //车牌号
    String driverName; //司机姓名
    String status; //状态
    String currentLocation; //当前位置
    String estimatedDepartureTime; //预计发车时间
    String estimatedArrivalTime; //预计到达时间
}
