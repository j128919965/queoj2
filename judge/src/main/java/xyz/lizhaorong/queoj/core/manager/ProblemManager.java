package xyz.lizhaorong.queoj.core.manager;

import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.entity.Problem;
import xyz.lizhaorong.queoj.util.http.lzrapp.ResponseBasedHttpClient;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProblemManager {

    private static final String API_PROBLEM = "http://localhost:8888/oj/problem/";
    private final ConcurrentHashMap<Integer, Problem> problems = new ConcurrentHashMap<>();

    public Problem getProblem(int pid) throws IOException {
        Problem problem = problems.get(pid);
        if (problem==null){
            problem = getProblemFromWeb(pid);
            problems.put(pid,problem);
        }
        return problem;
    }

    private Problem getProblemFromWeb(int pid) throws IOException {
        ResponseBasedHttpClient baseHttpClient = new ResponseBasedHttpClient();
        return baseHttpClient.get(API_PROBLEM+pid, null, Problem.class);
    }

}
