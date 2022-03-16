package com.netdisk.service.impl;

import com.netdisk.entity.User;
import com.netdisk.mapper.UserMapper;
import com.netdisk.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User userLogin(User user) {
        User userInfo = userMapper.findUserByUsername(user);  //查询到的用户信息
        if (userInfo == null || !userInfo.getPassword().equals(user.getPassword()))  //用户不存在或密码不正确
            return null;
        userInfo.setPassword("");  //隐藏用户密码
        return userInfo;
    }

    @Override
    public User findUserByUsername(User user){
        return userMapper.findUserByUsername(user);
    }

    @Override
    public boolean userExist(User user) {
        return userMapper.findUserByUsername(user) != null;
    }

    @Override
    public boolean checkToken(User user) {
        return userMapper.findUserByUsername(user).getToken().equals(user.getToken());
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public int insertUserInfo(User user) {
        return userMapper.insertUserInfo(user);
    }

    @Override
    public List<User> findUsers(User user) {
        return userMapper.findUsers(user);
    }


}
