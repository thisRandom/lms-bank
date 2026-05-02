package cn.edu.sjziei.lms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationTrackVo {
    Long id;
    Long dispatchId;
    BigDecimal latitude;
    BigDecimal longitude;
    String location;
    String recordTime;
}