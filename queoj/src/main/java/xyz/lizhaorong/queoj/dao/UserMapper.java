package xyz.lizhaorong.queoj.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import xyz.lizhaorong.queoj.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {
    @Select("select * from user where userName = #{username}")
    User getUserByUserName(String username);

    @Update("update user set ${prop}=#{val} where id=#{uid}")
    boolean updateUserInfo(@Param("prop") String prop ,@Param("val") String val,@Param("uid") Integer uid);

    @Select("select count(*) from user where point > (select point from user where id=#{id})")
    Integer getRank(Integer id);

    List<Integer> getUserCount(Integer uid);
}