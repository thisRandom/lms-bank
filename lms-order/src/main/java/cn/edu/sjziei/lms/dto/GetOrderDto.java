package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDto {
    Integer page=1; //页码
    Integer size=10; //页数
    String orderNo; //订单号
    String status; //状态
    String startDate; //开始日期
    String endDate; //结束日期
}
