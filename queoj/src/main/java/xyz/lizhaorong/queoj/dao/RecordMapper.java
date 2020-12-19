package xyz.lizhaorong.queoj.dao;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import xyz.lizhaorong.queoj.controller.vo.UserCenterRecord;
import xyz.lizhaorong.queoj.entity.Record;

import java.util.List;

public interface RecordMapper extends Mapper<Record> {

    @Select("select count(1) from record where pid = #{pid}")
    Integer exsitsWithPid(Integer pid);

    List<UserCenterRecord> getSimpleRecords(Integer uid);

    @Select("select state from record where id=#{rid}")
    Byte getRecordState(Integer rid);
}