package com.example.singleusermultiloginrestriction.service;

import com.example.singleusermultiloginrestriction.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    // 判断sessionId是否存在于redis中，如果存在，说明登录过了,用户可以直接登录
    public User isContainedInRedis(String sessionId)  {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Object,Object> users = redisTemplate.opsForHash().entries("user");
        System.out.println(users.size());
        for (Map.Entry<Object, Object> entry : users.entrySet()) {
            try {
                User user = objectMapper.readValue((String) entry.getValue(), User.class);
                if(user.getSessionId().equals(sessionId)){
                    return user;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    // 存储user对象
    public void store(User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(user);
        redisTemplate.opsForHash().put("user",user.getUsername(),objectMapper.writeValueAsString(user));
    }
    // 判断是否是同一设备，否则将其下线
    public User isSameDeviceAndDownload(String username,String sessionId) {
        System.out.println("username:"+username);
        Object user = redisTemplate.opsForHash().get("user", username);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user1 = objectMapper.readValue((String) user, User.class);
            //说明不是同一设备
            if(!user1.getSessionId().equals(sessionId)){
                user1.setSessionId(sessionId);
                redisTemplate.opsForHash().put("user",username,objectMapper.writeValueAsString(user1));
            }
            return user1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
