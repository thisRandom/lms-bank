package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignDispatchDto {
    @NotBlank(message = "签收人姓名不能为空")
    String signName;
}