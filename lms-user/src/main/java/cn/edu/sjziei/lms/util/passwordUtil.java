package cn.edu.sjziei.lms.util;

import org.springframework.stereotype.Component;

@Component
public class passwordUtil {
    public boolean isPasswordValid(String password){
        String regex = "^(?![a-zA-Z]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,16}$";
        return password.matches(regex);
    }
}
