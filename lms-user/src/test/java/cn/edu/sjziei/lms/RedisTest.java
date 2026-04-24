package cn.edu.sjziei.lms;

import cn.edu.sjziei.lms.common.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisUtil redisUtil;
    @Test
    public void RedisTest1(){
        String key = "user:" + "quanyiye";
        String value = UUID.randomUUID().toString();
        redisUtil.set(key,value,60*60*2 );
    }
    @Test
    public void RedisTest2(){
        Integer id=1;
        redisUtil.del("auth:"+id);
    }

    @Test
    public void isPasswordValid() {
        String password="12345678qq";
        // 包含数字、大小写字母、特殊符号（四选三），长度8-16位
        String regex = "^(?![a-zA-Z]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,16}$";
        System.out.println(password.matches(regex));
    }
}
