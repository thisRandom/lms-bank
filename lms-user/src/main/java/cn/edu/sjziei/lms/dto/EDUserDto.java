package cn.edu.sjziei.lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EDUserDto {
    @NotNull(message = "不能为空")
    Integer status;
    Integer id;
}
