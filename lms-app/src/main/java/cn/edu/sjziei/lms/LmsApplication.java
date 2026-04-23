package cn.edu.sjziei.lms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.edu.sjziei.lms")
@MapperScan("cn.edu.sjziei.lms.**.mapper")
public class LmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }
}
