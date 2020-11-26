package xyz.lizhaorong.queoj.core.manager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.entity.Record;
import xyz.lizhaorong.queoj.util.http.lzrapp.ResponseBasedHttpClient;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class RecordManager implements InitializingBean {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    private BoundListOperations<String,Object> redisList;

    private final BlockingQueue<Record> records = new ArrayBlockingQueue<Record>(16);

    private static final String API_RECORD = "http://localhost:8888/oj/submit/";

    public Record getRecord(int id) throws IOException {
        ResponseBasedHttpClient baseHttpClient = new ResponseBasedHttpClient();
        return baseHttpClient.get(API_RECORD+id, null, Record.class);
    }

    public Record getRecord(){
        Object o = redisList.leftPop(10, TimeUnit.SECONDS);
        System.out.println(o);
        return (Record) o;
    }

    public boolean saveRecord(Record record){
        try{
            records.put(record);
        }catch (InterruptedException e){
            return false;
        }
        return true;
    }

    private void postRecord(Record record) throws IOException {
        ResponseBasedHttpClient baseHttpClient = new ResponseBasedHttpClient();
        baseHttpClient.post(API_RECORD, record, Boolean.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisList = redisTemplate.boundListOps("records");
        new Thread(()->{
            while (true){
                try {
                    Record record = records.take();
                    try {
                        postRecord(record);
                    }catch (IOException e){
                        records.put(record);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
