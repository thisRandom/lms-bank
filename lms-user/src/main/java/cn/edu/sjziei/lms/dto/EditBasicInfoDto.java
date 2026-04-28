package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditBasicInfoDto {
    @NotNull(message = "姓名不能为空")
    String realName; //真实姓名
    @NotNull(message = "手机号不能为空")
    String phone;
}
