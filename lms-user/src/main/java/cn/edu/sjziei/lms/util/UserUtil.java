package cn.edu.sjziei.lms.util;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    /**
     * 检验用户名是否合法
     * */
    public boolean inspectionUn(String username){
        String regex="^[a-zA-Z_\\u4e00-\\u9fa5][a-zA-Z0-9_\\u4e00-\\u9fa5]{3,19}$";
        return username.matches(regex);
    }

    /**
     * 检验手机号
     * */
    public boolean inspectionP(String phone){
        String regex="^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }
}
