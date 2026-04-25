package cn.edu.sjziei.lms;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;

public class PasswordTest {
    @Test
    public void test1(){
        String hashpw=BCrypt.hashpw("123456Abc", BCrypt.gensalt());
        System.out.println(hashpw);
    }
}
