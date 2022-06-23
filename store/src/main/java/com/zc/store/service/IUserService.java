package com.zc.store.service;

import com.zc.store.entity.User;

import java.util.Date;

public interface IUserService {

    /**
     * 用户注册方法
     * @param user
     */
    void insertUser(User user);

    User login(String username, String password);

    void updateUserPassword(String username, String oldPassword, String newPassword);

    User getByUid(Integer uid);

    /**
     * 修改用户个人信息
     * @param uid 根据id查出你要修改的用户
     * @param username
     * @param user 封装你修改的数据
     */
    void updateUserInfo(Integer uid, String username, User user);

    /**
     * 修改用户头像
     * @param uid 当前用户的id
     * @param username 当前用户的用户名
     * @param avatar 要修改的目标头像的新的路径
     */
    void updateAvatarByUid(Integer uid, String username, String avatar);
}
