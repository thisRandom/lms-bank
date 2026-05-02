package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsVo {
    Long id; // 订单ID
    String orderNo; // 订单号
    String shipperName; // 发货人
    String shipperPhone; // 发货人电话
    String shipperAddress; // 发货地址
    String receiverName; // 收货人
    String receiverPhone; // 收货人电话
    String receiverAddress; // 收货地址
    String goodsType; // 货物类型
    Double weight; // 重量
    Double volume; // 体积
    String status; // 状态
    String remark; // 备注
    DispatchVo dispatch; // 调度信息
    String createTime;
}
