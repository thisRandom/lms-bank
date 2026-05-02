package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateDispatchStatusDto {
    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "^(IN_TRANSIT|ARRIVED)$", message = "状态只能是 IN_TRANSIT 或 ARRIVED")
    String status;
}