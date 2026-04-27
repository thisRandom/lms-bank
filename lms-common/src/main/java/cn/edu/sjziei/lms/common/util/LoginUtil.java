package cn.edu.sjziei.lms.common.util;

import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class LoginUtil {
    // 16位密钥，前后端必须一致
    @Value("${secret-key.ePassword}")
    String secretKey;
    private SymmetricCrypto symmetricCrypto;

    public String ePToPassword(String ep){
        this.symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey.getBytes());
        // 1. 参数校验
        if (ep == null || ep.trim().isEmpty()) {
            throw new IllegalArgumentException("密文不能为空");
        }

        // 2. 解密并明确异常
        try {
            return symmetricCrypto.decryptStr(ep);
        } catch (CryptoException e) {
            // 3. 记录日志并抛出有意义的异常
            throw new RuntimeException("密码解密失败，请检查密文格式或密钥配置", e);
        }
    }
}
