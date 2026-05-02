package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVehicleByIdVo {
    Double loadCapacity;
    Double volume;
    String status;
    Long driverId;
    String currentLocation;
}
