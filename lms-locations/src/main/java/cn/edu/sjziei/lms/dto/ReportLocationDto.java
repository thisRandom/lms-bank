package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReportLocationDto {
    @NotNull(message = "调度ID不能为空")
    Long dispatchId;

    @NotNull(message = "纬度不能为空")
    BigDecimal latitude;

    @NotNull(message = "经度不能为空")
    BigDecimal longitude;

    String location;

    Long id;

    Date recordTime;
}