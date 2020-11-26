package xyz.lizhaorong.queoj.service;

import cn.hutool.core.lang.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.lizhaorong.queoj.controller.vo.SimpleProblem;
import xyz.lizhaorong.queoj.dao.ProblemMapper;
import xyz.lizhaorong.queoj.entity.Problem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProblemService implements InitializingBean {

    private static final String PRO_TIMES_KEY = "proTimes";

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    BoundZSetOperations<String, String> zSetOps;

    @Override
    public void afterPropertiesSet() throws Exception {
        zSetOps = redisTemplate.boundZSetOps(PRO_TIMES_KEY);
    }

    /**
     * 获取题目列表
     * @param from
     * @param limit
     * @return
     */
    public List<SimpleProblem> getProblems(Integer from, Integer limit){
        return problemMapper.getProblemList(from,limit);
    }

    /**
     * 模糊搜索获取题目
     * @param like
     * @return
     */
    public List<SimpleProblem> getProblems(String like){
        return problemMapper.getProblemLike(like);
    }

    /**
     * 通过题目id获取完整信息
     *
     * 同时，会更新redis中题目访问量
     * @param id
     * @return
     */
    public Problem getProblemById(Integer id){
        Problem problem = problemMapper.selectByPrimaryKey(id);
        if (problem!=null){
            zSetOps.incrementScore(problem.getName()+"_"+id,1);
        }
        return problem;
    }

    /**
     * 获取全部题目数量
     * @return
     */
    public Integer getProblemsTotal(){
        return problemMapper.getProblemTotal();
    }

    /**
     * 通过redis，获取最热的几道题
     * @return
     */
    public Set<String> getHotTenProblems() {
        return zSetOps.reverseRange(0, 9);
    }

    public void addProblem(Problem problem){
        problemMapper.insertSelective(problem);
    }
}
