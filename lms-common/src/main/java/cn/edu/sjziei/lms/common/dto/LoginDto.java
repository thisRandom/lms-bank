package cn.edu.sjziei.lms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    String username;
    String password;
    String uuid;
    String code;
}
