package cn.edu.sjziei.lms.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateVehicleDto {
    String vehicleType;
    BigDecimal loadCapacity;
    Double volume;
    Long driverId;
    String status;
}
