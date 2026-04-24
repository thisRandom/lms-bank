package cn.edu.sjziei.lms.util;

import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.vo.LoginVo;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class tokenUtil {
    @Value("${secret-key.jwt}")
    String secretKey;

    /**
     * 登录时得到token
     * */
    public String getToken(long timeMillis, LoginVo loginVo){
        String token = JWT.create()
                .setKey(secretKey.getBytes())
                .setExpiresAt(new Date(timeMillis))
                .setPayload("user",loginVo)
                .sign();
        return token;
    }

    /**
     * 解析token
    * */
    public LoginVo analysisToken(String token){
        JWT jwt=null;
        try{
            jwt=JWT.of(token);
        }catch (Exception e){
            return null;
        }

        // 类型转换
        JSONObject userJson = (JSONObject) jwt.getPayload("user");
        LoginVo loginVo = userJson.toBean(LoginVo.class);
        return loginVo;
    }
}
