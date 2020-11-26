package xyz.lizhaorong.queoj.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.lizhaorong.queoj.controller.vo.UserInfo;
import xyz.lizhaorong.queoj.entity.User;
import xyz.lizhaorong.queoj.security.token.entity.TokenObject;
import xyz.lizhaorong.queoj.security.web.Authorization;
import xyz.lizhaorong.queoj.service.UserService;
import xyz.lizhaorong.queoj.support.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * {"uid":Integer
     *  "token":{
     *      "accessToken":String
     *      "refreshToken":String
     *  }
     * }
     */
    @PostMapping("/login")
    public Response<Map<String,Object>> login(String username, String password, HttpServletRequest request){
        Response<Map<String,Object>> response = new Response<>();

        User user = userService.login(username,password);
        if (user==null){
            response.failure("用户名或密码错误");
        }else {
            UserInfo info = new UserInfo();
            BeanUtils.copyProperties(user,info);
            Map<String,Object> map = Map.of("info",info,
                    "token",userService.generateToken(user,request)
            );
            response.success(map);
        }
        return response;
    }

    @PostMapping("/refresh")
    public Response<TokenObject> refresh(String token){
        TokenObject tokenObject = userService.refreshTokens(token);
        return new Response<TokenObject>().success(tokenObject);
    }

    @PutMapping("/register")
    public Response<Boolean> register(String username,String password){
        Response<Boolean> response = new Response<>();
        final boolean register = userService.register(username, password);
        if (register){
            response.success();
        }else {
            response.failure("用户名已重复");
        }
        return response;
    }

    @GetMapping("/info")
    @Authorization
    public Response<UserInfo> getUserInfo(Integer uid){
        Response<UserInfo> response = new Response<>();
        User user = userService.getUser(uid);
        if (user!=null){
            UserInfo info = new UserInfo();
            BeanUtils.copyProperties(user,info);
            response.success(info);
        }else {
            response.failure("用户不存在");
        }
        return response;
    }

    @GetMapping("/rank")
    @Authorization
    public Response<Integer> getRank(Integer id){
        Response<Integer> response = new Response<>();
        Integer rank = userService.getRank(id);
        return response.success(rank);
    }

    @GetMapping("/count")
    @Authorization
    public Response<Map<String,Integer>> getCount(Integer uid){
        return new Response<Map<String,Integer>>().success(userService.getCount(uid));
    }
}
