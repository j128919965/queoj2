package xyz.lizhaorong.queoj.service;

import org.attoparser.dom.INestableNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        redisList = redisTemplate.boundListOps("records");
    }

    public Integer submit(RecordVO record){
        Record r = new Record();
        BeanUtils.copyProperties(record,r);
        r.setTime(new Date());
        //pending
        r.setState((byte)2);
        redisList.rightPush(r);
        recordMapper.insertSelective(r);
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

    public Record getRecord(Integer id) {
        return recordMapper.selectByPrimaryKey(id);
    }
}
