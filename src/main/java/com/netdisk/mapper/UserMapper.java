package com.netdisk.mapper;

import com.netdisk.entity.User;

import java.util.List;

public interface UserMapper {

    public User findUserByUsername(User user);

    public int updateUserById(User user);

    public int insertUserInfo(User user);

    public List<User> findUsers(User user);
}

