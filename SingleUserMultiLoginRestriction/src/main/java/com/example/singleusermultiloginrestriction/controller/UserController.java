package com.example.singleusermultiloginrestriction.controller;

import com.example.singleusermultiloginrestriction.model.User;
import com.example.singleusermultiloginrestriction.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        String sessionId= request.getSession().getId();
        User user;
        // 找到了sessionId,并且在对象里存储着，说明是同一设备
        if ((user=userService.isContainedInRedis(sessionId))!=null) {
            request.setAttribute("user",user);
            return "index.html";
        }
        return "login.html";
    }
    // 这里直接使用postman进行输入了
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,HttpServletRequest request){
        User user=userService.isSameDeviceAndDownload(username, request.getSession().getId());
        request.setAttribute("user",user);
        return "index.html";
    }
    // 这里直接使用postman进行输入了
    @PostMapping("/register")
    public void register(@RequestParam("username") String username,@RequestParam("name") String name,HttpServletRequest request) throws JsonProcessingException {
        User user = new User(username,name, request.getSession().getId());
        userService.store(user);
    }
}
