package xyz.lizhaorong.queoj.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import xyz.lizhaorong.queoj.controller.vo.SimpleProblem;
import xyz.lizhaorong.queoj.entity.Problem;

import java.util.List;

public interface ProblemMapper extends Mapper<Problem> {

    @Select("select id,name,point,level from problem order by id limit #{limit}")
    List<SimpleProblem> getProblemList(Integer from , Integer limit);


    @Select("select id,name,point,level from problem where name like '%${p}%'")
    List<SimpleProblem> getProblemLike(@Param("p") String p);

    @Select("select count(*) from problem")
    Integer getProblemTotal();
}