package cn.edu.sjziei.lms;

import cn.edu.sjziei.lms.entity.User;
import cn.edu.sjziei.lms.mapper.UserMapper;
import cn.edu.sjziei.lms.util.loginUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    loginUtil loginUtil;
    @Test
    public void MapperTest1(){
        User user=new User();
        //user.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        user.setUsername("admin");
        System.out.println(user.getUsername());
//        System.out.println(userMapper.login(user).getId());
    }

    @Test
    void generateEp() {
        // 必须和 yml 里的 secret-key.ePassword 保持一致，且长度为 16 位
        String key = "1234567890123456";
        SymmetricCrypto crypto = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());

        // 加密 "123456"
        String encryptHex = crypto.encryptHex("123456789!Qyy");
        System.out.println(loginUtil.ePToPassword(encryptHex));
        System.out.println("生成的密文(ep)为: " + encryptHex);
    }
}
