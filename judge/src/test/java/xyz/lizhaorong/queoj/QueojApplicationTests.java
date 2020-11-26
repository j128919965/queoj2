package xyz.lizhaorong.queoj;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.lizhaorong.queoj.entity.Problem;
import xyz.lizhaorong.queoj.support.Response;
import xyz.lizhaorong.queoj.util.http.BaseHttpClient;
import xyz.lizhaorong.queoj.util.http.lzrapp.ResponseBasedHttpClient;

import java.io.IOException;

@SpringBootTest
class QueojApplicationTests {

    @Test
    void contextLoads() {


    }

    public static void main(String[] args) throws IOException {
        ResponseBasedHttpClient baseHttpClient = new ResponseBasedHttpClient();
        Problem p = baseHttpClient.get("http://localhost:8888/oj/problem/1", null, Problem.class);
        System.out.println(p);
    }

}
