package cn.edu.sjziei.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchEntity {
    Long vehicleId;
    Long driverId;
    String status;

}
