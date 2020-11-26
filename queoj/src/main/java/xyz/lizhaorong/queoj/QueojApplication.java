package xyz.lizhaorong.queoj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "xyz.lizhaorong.queoj.dao")
@EnableTransactionManagement
@EnableAsync
public class QueojApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueojApplication.class, args);
    }

}
