package xyz.lizhaorong.queoj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.lizhaorong.queoj.controller.vo.RecordVO;
import xyz.lizhaorong.queoj.controller.vo.UserCenterRecord;
import xyz.lizhaorong.queoj.entity.Record;
import xyz.lizhaorong.queoj.security.web.Authorization;
import xyz.lizhaorong.queoj.service.SubmitService;
import xyz.lizhaorong.queoj.support.Response;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/submit")
@Slf4j
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @PutMapping
    @Authorization
    public Response<Integer> submit(@Validated RecordVO record){
        Integer rid = submitService.submit(record);
        return new Response<Integer>().success(rid);
    }

    @GetMapping
    @Authorization
    public Response<List<Record>> getRecords(Integer uid,Integer pid){
        Response<List<Record>> response = new Response<>();
        List<Record> records = submitService.getRecords(uid,pid);
        return response.success(records);
    }

    @GetMapping("/state")
    @Authorization
    public Response getRecordState(Integer id){
        Response<Object> response = new Response<>();
        byte state = submitService.getRecordState(id);
        if (state==2)response.success(2);
        else {
            return getRecord(id);
        }
        return response;
    }

    @GetMapping("/{id}")
    @Authorization
    public Response<Record> getRecord(@PathVariable("id") Integer id){
        Response<Record> response = new Response<>();
        Record record = submitService.getRecord(id);
        return response.success(record);
    }

    @PostMapping
    public Response<Boolean> updateReord(Record record){
        log.debug("update ");
        System.out.println(record);
        submitService.updateRecord(record);
        return Response.staticSuccess();
    }


    @GetMapping("/simple")
    @Authorization
    public Response<List<UserCenterRecord>> getRecords(Integer id){
        Response<List<UserCenterRecord>> response = new Response<>();
        List<UserCenterRecord> records = submitService.getUserCenterRecords(id);
        return response.success(records);
    }



}
