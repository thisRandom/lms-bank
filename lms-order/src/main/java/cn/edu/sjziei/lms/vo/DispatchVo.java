package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchVo {
    Long id; // 调度ID
    String dispatchNo; // 调度单号
    String plateNumber; // 车牌号
    String driverName; // 司机姓名
    String driverPhone; // 司机电话
    String status; // 调度状态
    String currentLocation; // 当前位置
}
