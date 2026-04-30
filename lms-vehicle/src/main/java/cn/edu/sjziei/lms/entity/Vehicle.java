package cn.edu.sjziei.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    Long id;
    String plateNumber;
    String vehicleType;
    BigDecimal loadCapacity;
    Double volume;
    Long driverId;
    String status;
    String lastLocation;
    LocalDateTime lastUpdateTime;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    // Mapper映射字段
    private String driverName;
    private String driverPhone;
}
