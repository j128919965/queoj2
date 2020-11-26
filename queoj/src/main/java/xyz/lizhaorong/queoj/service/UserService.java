package xyz.lizhaorong.queoj.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.lizhaorong.queoj.dao.UserMapper;
import xyz.lizhaorong.queoj.entity.User;
import xyz.lizhaorong.queoj.security.token.TokenManager;
import xyz.lizhaorong.queoj.security.token.entity.SimpleUser;
import xyz.lizhaorong.queoj.security.token.entity.TokenObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenManager tokenManager;

    public User login(String username, String password) {
        password = SecureUtil.md5(password);

        User u = userMapper.getUserByUserName(username);
        if (u==null || !password.equals(u.getPassword())){
            return null;
        }
        return u;
    }

    public TokenObject refreshTokens(String token){
        return tokenManager.refresh(token);
    }

    public TokenObject generateToken(User u, HttpServletRequest request){
        SimpleUser simpleUser = new SimpleUser(u.getId().toString(), ServletUtil.getClientIP(request),u.getRole(),0);
        return tokenManager.generate(simpleUser);
    }

    public User getUser(Integer uid){
        return userMapper.selectByPrimaryKey(uid);
    }

    public boolean register(String username, String password) {
        User u = userMapper.getUserByUserName(username);
        if (u!=null) return false;
        password = SecureUtil.md5(password);
        u = new User();
        u.setUsername(username);
        u.setNickname(username);
        u.setPassword(password);
        final int i = userMapper.insertSelective(u);
        return true;
    }

    public boolean updateInfo(String prop , String val,int uid){
        return userMapper.updateUserInfo(prop,val,uid);
    }

    public Integer getRank(Integer id) {
        return userMapper.getRank(id)+1;
    }

    public Map<String,Integer> getCount(Integer uid){
        List<Integer> userCount = userMapper.getUserCount(uid);
        return Map.of("easy",userCount.get(0),"medium",userCount.get(1),"hard",userCount.get(2),"solved",userCount.get(0)+userCount.get(1)+userCount.get(2));
    }
}
