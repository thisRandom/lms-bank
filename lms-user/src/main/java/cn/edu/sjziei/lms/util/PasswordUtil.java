package cn.edu.sjziei.lms.util;

import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    public boolean isPasswordValid(String password){
        String regex = "^(?![a-zA-Z]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,16}$";
        return password.matches(regex);
    }

    /**
     * 得到加密后的密码
     * */
    public String encryptionPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 验证加密密码
     */
    public boolean verificationPassword(String password,String hashpw){
        return BCrypt.checkpw(password, hashpw);
    }
}
