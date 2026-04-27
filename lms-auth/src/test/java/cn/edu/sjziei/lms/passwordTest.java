package cn.edu.sjziei.lms;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
public class passwordTest {

    // 模拟你类中的 secretKey，确保加密和解密使用的是同一个 key
    private String secretKey = "1234567890123456"; // AES 密钥通常为 16/24/32 位

    @Test
    public void testPasswordEncryption() {
        // 1. 准备原始明文密码
        String rawPassword = "123456789Abc";

        // 2. 初始化加密对象 (模拟你的业务逻辑)
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey.getBytes());

        // 3. 执行加密：将明文转为密文 (Base64 或十六进制字符串)
        String encryptHex = symmetricCrypto.encryptHex(rawPassword);
        System.out.println("生成的密文: " + encryptHex);

        // 4. 执行解密校验：调用你写的解密方法（或者直接解密测试）
        String decryptedPassword = symmetricCrypto.decryptStr(encryptHex);

        // 5. 断言结果
        Assertions.assertEquals(rawPassword, decryptedPassword, "解密后的字符串应与原始密码一致");
        Assertions.assertNotNull(encryptHex, "加密结果不应为空");

    }
}
