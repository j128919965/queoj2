package xyz.lizhaorong.queoj.dao;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import xyz.lizhaorong.queoj.controller.vo.UserCenterRecord;
import xyz.lizhaorong.queoj.entity.Record;

import java.util.List;

public interface RecordMapper extends Mapper<Record> {
    List<UserCenterRecord> getSimpleRecords(Integer uid);
}