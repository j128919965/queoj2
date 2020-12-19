package xyz.lizhaorong.queoj;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.lizhaorong.queoj.dao.RecordMapper;

@SpringBootTest
class QueojApplicationTests {

    @Autowired
    RecordMapper recordMapper;

    @Test
    void contextLoads() {
        System.out.println(recordMapper.exsitsWithPid(1));

    }

    public static void main(String[] args) {
        final String j128919965 = SecureUtil.md5("j128919965");
        System.out.println(j128919965);
    }

}
