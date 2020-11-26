package xyz.lizhaorong.queoj.controller;

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
@Authorization
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @PutMapping
    public Response<Integer> submit(@Validated RecordVO record){
        Integer rid = submitService.submit(record);
        return new Response<Integer>().success(rid);
    }

    @GetMapping
    public Response<List<Record>> getRecords(Integer uid,Integer pid){
        Response<List<Record>> response = new Response<>();
        List<Record> records = submitService.getRecords(uid,pid);
        return response.success(records);
    }

    @GetMapping("/{id}")
    public Response<Record> getRecord(@PathVariable("id") Integer id){
        Response<Record> response = new Response<>();
        Record record = submitService.getRecord(id);
        return response.success(record);
    }

    @PostMapping
    public Response<Boolean> updateReord(Record record){
        //TODO
        return Response.staticSuccess();
    }


    @GetMapping("/simple")
    public Response<List<UserCenterRecord>> getRecords(Integer id){
        Response<List<UserCenterRecord>> response = new Response<>();
        List<UserCenterRecord> records = submitService.getUserCenterRecords(id);
        return response.success(records);
    }

}
