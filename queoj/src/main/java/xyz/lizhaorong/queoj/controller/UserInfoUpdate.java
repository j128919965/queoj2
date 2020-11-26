package xyz.lizhaorong.queoj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lizhaorong.queoj.security.web.Authorization;
import xyz.lizhaorong.queoj.service.UserService;
import xyz.lizhaorong.queoj.support.Response;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/info")
@Authorization
public class UserInfoUpdate {

    @Autowired
    UserService userService;

    @PostMapping("/nickname")
    public Response nickname(String data,Integer uid){
        boolean b = userService.updateInfo("nickname", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

    @PostMapping("/gender")
    public Response gender(Byte data,Integer uid){
        boolean b = userService.updateInfo("gender", String.valueOf(data), uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

    @PostMapping("/phone")
    public Response phone(@Pattern(regexp = "\\d{11}") String data, Integer uid){
        boolean b = userService.updateInfo("phone", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

    @PostMapping("/email")
    public Response email(@Email String data, Integer uid){
        boolean b = userService.updateInfo("email", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

    @PostMapping("/introduction")
    public Response introduction(String data,Integer uid){
        boolean b = userService.updateInfo("introduction", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

    @PostMapping("/github")
    public Response github(String data,Integer uid){
        boolean b = userService.updateInfo("github", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }
    @PostMapping("/website")
    public Response website(String data,Integer uid){
        boolean b = userService.updateInfo("website", data, uid);
        if (b)return Response.staticSuccess();
        else {
            return new Response().failure("未知错误");
        }
    }

}
