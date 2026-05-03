package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDetailVo {
    Long id; // 调度ID
    String dispatchNo; // 调度单号
    Long orderId; // 订单ID
    String orderNo; // 订单号
    Long vehicleId; // 车辆ID
    String plateNumber; // 车牌号
    Long driverId; // 司机ID
    String driverName; // 司机姓名
    String driverPhone; // 司机电话
    String status; // 状态
    String currentLocation; // 当前位置
    String estimatedDepartureTime; // 预计发车时间
    String estimatedArrivalTime; // 预计到达时间
    String actualDepartureTime; // 实际发车时间
    String actualArrivalTime; // 实际到达时间
    String signName; // 签收人
    String remark; // 备注
    String createTime; // 创建时间
    String shipperName; //发货人姓名
    String shipperPhone; //发货人电话
    String shipperAddress; //发货人详细地址
    String receiverName; //发货人姓名
    String receiverPhone; //发货人电话
    String receiverAddress; //发货人详细地址
    String goodsType; //货物类型
}