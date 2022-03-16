package com.netdisk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netdisk.entity.User;
import com.netdisk.service.UserService;
import com.netdisk.utils.InputCheck;
import com.netdisk.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/user")
@Controller
public class UserController {

    @Resource(description = "userService")
    private UserService userService;

    private static final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    @RequestMapping("/login")
    @ResponseBody
    public String login(User user) throws JsonProcessingException {

        User login = userService.userLogin(user);
        if (login == null)
            return "用户名和密码不匹配";
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            String valueAsString = objectMapper.writeValueAsString(login);
//            System.out.println("json字符串："+valueAsString);
            return valueAsString;
        }
    }

    @RequestMapping("/login/check")
    @ResponseBody
    public String loginCheck(User user) {  //校验用户登录状态
        boolean checkToken = userService.checkToken(user);
        if (checkToken)
            return "true";
        else
            return "false";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(User user, @RequestParam(required = true) String old_password) throws JsonProcessingException {
        if (!InputCheck.nicknameCheck(user.getNickname()))
            return "1";
        User tmp = new User();
        //未输入密码，则认为用户想要修改昵称
        if ("".equals(user.getPassword()) && "".equals(old_password)) {
            if (userService.checkToken(user)) {
                tmp.setUsername(user.getUsername());
                tmp.setNickname(user.getNickname());
                int status = userService.updateUserInfo(tmp);
                return status == 1 ? "5" : "7";
            } else
                return "4";
        }
        //检查密码格式
        if (!InputCheck.passwordCheck(user.getPassword()) || !InputCheck.passwordCheck(old_password))
            return "2";
        //临时用户信息，存放用户原始的用户名和密码
        tmp.setNickname(user.getNickname());
        tmp.setUsername(user.getUsername());
        tmp.setPassword(old_password);
        String newToken = String.valueOf(snowflakeIdWorker.nextId());
        tmp.setToken(newToken);  //更改密钥
        User userInfo = userService.userLogin(tmp);  //使用用户原始信息登录，如果能登录成功，则身份校验通过，允许用户更新信息
        if (userInfo != null) {  //身份校验成功
            tmp.setPassword(user.getPassword());
            int status = userService.updateUserInfo(tmp);
            return status == 1 ? "6" : "7";
        }
        return "3";
    }

    @RequestMapping("/new")
    @ResponseBody
    public String newUser(User user) {
        if (!InputCheck.usernameCheck(user.getUsername()))
            return "2";
        else if (!InputCheck.nicknameCheck(user.getNickname()))
            return "3";
        else if (!InputCheck.passwordCheck(user.getPassword()))
            return "4";
        else if (userService.userExist(user))
            return "5";
        String id = String.valueOf(snowflakeIdWorker.nextId());
        user.setId(id);
        user.setToken(id);
        user.setCapacity(104857600);
        user.setLevel(1);
        return userService.insertUserInfo(user) == 1 ? "1" : "0";
    }

}
