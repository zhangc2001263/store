package com.zc.store.mapper;

import com.zc.store.entity.User;

import java.util.Date;

/**
 * 用户模块的持久层接口
 */
public interface UserMapper {

    /**
     * 注册用户，相当于往数据库中添加数据
     *
     * @param user 用户的数据
     * @return 返回值为Integer，表示数据库的操作条数
     */
    Integer insertUser(User user);

    /**
     * 这个是查询数据库中的用户，用来查看新注册的用户是否已存在
     *
     * @param username 根据用户名查，查到User信息就表示已存在
     * @return 用户信息，找到了就返回这个用户信息，没有找到就返回null
     */
    User queryUserByUsername(String username);

    User findByid(Integer uid);

    /**
     * 修改密码
     *
     * @param username 根据用户名查找
     * @param password 修改的密码
     */
    Integer updateUserPassword(String username, String password, String modifiedUser, Date modifiedTime);

    /**
     * 修改用户个人资料
     *
     * @param user user对象
     * @return 更新数据库操作，返回操作sql的条数
     */
    Integer updateUserInfo(User user);

    /**
     * 修改用户头像
     * @param uid 根据uid修改
     * @param avatar 头像
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateAvatarByUid(Integer uid, String avatar, String modifiedUser, Date modifiedTime);
}
