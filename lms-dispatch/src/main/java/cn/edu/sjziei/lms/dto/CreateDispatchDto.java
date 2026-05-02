package cn.edu.sjziei.lms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDispatchDto {
    @NotNull(message = "订单ID不能为空")
    Long orderId; //订单ID
    @NotNull(message = "车辆ID不能为空")
    Long vehicleId;//车辆ID
    @NotNull(message = "司机ID不能为空")
    Long driverId;//司机ID
    @NotNull(message = "预计发车时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime estimatedDepartureTime;//预计发车时间
    @NotNull(message = "预计到达时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime  estimatedArrivalTime;//预计到达时间
    String remark; //备注
    String dispatchNo; //调度单号
    String status; //状态
}
