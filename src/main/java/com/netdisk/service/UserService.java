package com.netdisk.service;

import com.netdisk.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 用户登录
     * @param user user对象中只需要包含username和password
     * @return  如果用户名密码校验成功返回不包含password的用户信息，否则返回null
     */
    public User userLogin(User user);

    /**
     * 查询用户是否存在
     * @param user  user对象中需要包含username
     * @return  存在返回true，否则返回false
     */
    public boolean userExist(User user);

    /**
     * 验证用户的登录状态，当用户成功登录后，每次登录只需验证用户名与token是否正确。若不正确则注销登录。
     * @param user user对象中包含username和token
     * @return  用户名是否与token匹配
     */
    public boolean checkToken(User user);

    /**
     * 根据id更新用户信息
     * @param user user中至少包含一个非空非零的nickname password capacity level token
     * @return 用户表受影响的行数
     */
    public int updateUserInfo(User user);

    public int insertUserInfo(User user);

    /**
     * 查找用户群体 （管理员）
     * @param user 管理员信息，需要包含level
     * @return  等级比当前管理员等级低的用户
     */
    public List<User> findUsers(User user);

    public User findUserByUsername(User user);
}
