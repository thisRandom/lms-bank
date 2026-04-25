package cn.edu.sjziei.lms.common.util;

import cn.edu.sjziei.lms.common.vo.LoginVo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    @Value("${secret-key.jwt}")
    String secretKey;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 登录时得到token
     * */
    public String getToken(long timeMillis, LoginVo loginVo, String value){
        String token = JWT.create()
                .setKey(secretKey.getBytes())
                .setExpiresAt(new Date(timeMillis))
                .setPayload("user",loginVo)
                .setPayload("value",value)
                .sign();
        return token;
    }

    /**
     * 解析token中的loginVo类
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

    /**
    * 解析token中的value
    * */
    public String analysisTokenToValue(String token){
        JWT jwt=null;
        try{
            jwt=JWT.of(token);
        }catch (Exception e){
            return null;
        }

        // 类型转换
        String value= (String) jwt.getPayload("value");
        return value;
    }


    /**
     * 验证token有效性
     * */
    public boolean verificationToken(String token){
        //验证token有效性
        if (!JWTUtil.verify(token, secretKey.getBytes())) {
            return false;
        }
        //检验token是否过期
        try{
            JWTValidator.of(token).validateDate();
        }catch (Exception e){
            return false;
        }
        return true;
    }


    /**
     * 验证token里的值与redis是否相同
     * */
    public boolean tokenValueEqualToRedis(String token){
        Integer id=this.analysisToken(token).getId();
        String value = this.analysisTokenToValue(token);
        String redisValue = (String)redisUtil.get("auth:" + id);

        if (StrUtil.equals(value,redisValue)) {
            return true;
        }
        return false;
    }
}
