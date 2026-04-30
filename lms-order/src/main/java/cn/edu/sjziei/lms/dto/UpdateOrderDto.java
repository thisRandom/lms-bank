package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDto {
    Long id; //订单ID
    String shipperName; //发货人
    String shipperPhone; //发货人电话
    String shipperAddress; //发货地址
    String receiverName; //收货人
    String receiverPhone; //收货人电话
    String receiverAddress; //收货地址
    String goodsType; //货物类型
    BigDecimal weight; //重量（吨）
    BigDecimal volume; //体积（方）
    String remark; //备注
}
