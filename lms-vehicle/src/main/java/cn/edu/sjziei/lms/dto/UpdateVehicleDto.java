package cn.edu.sjziei.lms.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateVehicleDto {
    private String vehicleType;
    private BigDecimal loadCapacity;
    private Long driverId;
    private String status;
}
