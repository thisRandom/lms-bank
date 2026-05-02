package cn.edu.sjziei.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDispatchListDto {
    Integer page=1; //页码
    Integer size=10; //每页条数
    Integer dispatchNo; //调度单号
    String status; //状态
    Long driverId; //司机id
}
