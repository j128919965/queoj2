package xyz.lizhaorong.queoj;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueojApplicationTests {

    @Test
    void contextLoads() {


    }

    public static void main(String[] args) {
        final String j128919965 = SecureUtil.md5("j128919965");
        System.out.println(j128919965);
    }

}
