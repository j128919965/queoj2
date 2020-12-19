package xyz.lizhaorong.queoj.service;

import org.attoparser.dom.INestableNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.lizhaorong.queoj.controller.vo.RecordVO;
import xyz.lizhaorong.queoj.controller.vo.UserCenterRecord;
import xyz.lizhaorong.queoj.dao.RecordMapper;
import xyz.lizhaorong.queoj.entity.Record;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SubmitService implements InitializingBean {

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    private BoundListOperations<String,Object> redisList;

    private BoundHashOperations<String,Object,String> redisHash;

    @Override
    public void afterPropertiesSet() throws Exception {
        redisList = redisTemplate.boundListOps("records");
        redisHash = redisTemplate.boundHashOps("record_status");
    }

    public Integer submit(RecordVO record){
        Record r = new Record();
        BeanUtils.copyProperties(record,r);
        r.setTime(new Date());
        //pending
        r.setState((byte)2);
        recordMapper.insertSelective(r);
        redisList.rightPush(r);
        redisHash.put(r.getId().toString(),r.getState().toString());
        return r.getId();
    }

    public List<UserCenterRecord> getUserCenterRecords(Integer uid) {
        return recordMapper.getSimpleRecords(uid);
    }

    public List<Record> getRecords(Integer uid, Integer pid) {
        Record r = new Record();
        r.setUid(uid);
        r.setPid(pid);
        return recordMapper.select(r);
    }

    public Byte getRecordState(Integer rid){
        return Byte.valueOf(redisHash.get(rid.toString()));
    }

    public Record getRecord(Integer id) {
        return recordMapper.selectByPrimaryKey(id);
    }

    public void updateRecord(Record record){
        redisHash.put(record.getId().toString(),record.getState().toString());
        recordMapper.updateByPrimaryKey(record);
    }
}
