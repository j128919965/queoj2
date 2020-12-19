package xyz.lizhaorong.queoj;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.lizhaorong.queoj.core.manager.ProblemManager;
import xyz.lizhaorong.queoj.entity.Problem;
import xyz.lizhaorong.queoj.support.Response;
import xyz.lizhaorong.queoj.util.http.BaseHttpClient;
import xyz.lizhaorong.queoj.util.http.lzrapp.ResponseBasedHttpClient;

import java.io.IOException;

@SpringBootTest
class QueojApplicationTests {

    @Autowired
    ProblemManager problemManager;

    @Test
    void contextLoads() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        String s= "int show()";
        char[] chars = s.toCharArray();
        int f=0,g=0;
        for (int i=0;i<chars.length;i++){
            if (chars[i]==' ')f=i+1;
            if (chars[i]=='(')g=i;
        }
        System.out.println(s.substring(f,g));
    }

}
