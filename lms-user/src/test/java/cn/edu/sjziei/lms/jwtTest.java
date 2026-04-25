package cn.edu.sjziei.lms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {
    @Test
    public void jwtTest1() {
        Map<String, Object> map = new HashMap<String, Object>() {
            //private static final long serialVersionUID = 1L;
            {
                put("id", Integer.parseInt("123"));
                put("exp", System.currentTimeMillis() + 1000 * 3);
            }
        };

        String token = JWTUtil.createToken(map, "123456".getBytes());


        //String token=JWTUtil.createToken(map, "123456".getBytes());
        System.out.println(token);

    }

    @Test
    public void jwtTest2() {
        System.out.println(JWTUtil.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3NzY5NTQyNDgsImV4cCI6MTc3Njk1NDI1MSwibWFwIjp7ImV4cCI6MTc3Njk1NDI1MTYwNCwiaWF0IjoxNzc2OTU0MjQ4NjA0LCJ1c2VybmFtZSI6InRlc3QifX0.-yx8OYFaTTXaAJapFIQnBl2hg4wMa9bkBKdVOinRp8Q", "secret_key".getBytes()));
    }


    @Test
    public void jwtTest3() throws InterruptedException {
        // 1. 生成 token（过期时间 3 秒）
        Map<String, Object> map = new HashMap<>();
        map.put("id", 123);
        map.put("expire_time", System.currentTimeMillis() + 1000 * 3);  // 3秒过期

        String token = JWTUtil.createToken(map, "123456".getBytes());
        System.out.println("Token: " + token);

        // 2. 立即验证（应该 true）
        System.out.println("立即验证: " + JWTUtil.verify(token, "123456".getBytes()));

        // 3. 等待 5 秒
        Thread.sleep(5000);

        // 4. 再次验证（应该 false）
        System.out.println("5秒后验证: " + JWTUtil.verify(token, "123456".getBytes()));
    }

    @Test
    public void jwtTest4()throws InterruptedException{
        Map payload = new HashMap<>();
        long now = System.currentTimeMillis();
        long expire = now + 3* 1000; // 30分钟后过期

        payload.put("username", "test");

        JWTSigner signer = JWTSignerUtil.hs256("secret_key".getBytes());
        String token = JWT.create()
                .setIssuedAt(new Date(now))
                .setExpiresAt(new Date(now+3000))
                .setPayload("map",payload)
                .setSigner(signer)
                .sign();

        //Thread.sleep(3000);
        System.out.println(JWTUtil.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzcxMDI0NDMsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoiYWRtaW4iLCJyZWFsTmFtZSI6Iuezu-e7n-euoeeQhuWRmCIsInJvbGUiOiJBRE1JTiIsInJvbGVJZCI6MX0sInZhbHVlIjoiNWUwYzQxZTctNzdjMy00Zjg0LTlmNGUtMDYzMTIzMTUzNmJjIn0.PS9eas-WLNAWp-HOOZ7zNOG0ltRHB85CTKJ0wPmElgI", signer));
        System.out.println(JWTValidator.of(token).validateDate());
    }

    @Test
    public void jwtTest5() throws InterruptedException {
        //设置token
        final String token = JWT.create()
                .setKey("123456".getBytes())
                .setExpiresAt(new Date(System.currentTimeMillis()+1000*5))
                .setPayload("user","user1")
                .sign();
        Thread.sleep(1000*6);
        //验证token有没有过期
        System.out.println(JWTValidator.of(token).validateDate());

//        JWT jwt= JWT.of(token);
//        System.out.println(jwt.getPayload("user"));
    }
    @Test
    public void Test6(){
        System.out.println(System.currentTimeMillis() + 1000 * 60 * 60 * 2);
        long now = System.currentTimeMillis();
        long after2h = now + 1000 * 60 * 60 * 2;
        System.out.println("当前 UTC 毫秒: " + now);
        System.out.println("+2h 毫秒: " + after2h);

// 打印本地时间（JVM默认时区）
        System.out.println("当前本地时间: " + new Date(now));
        System.out.println("+2h 本地时间: " + new Date(after2h));
    }
}
