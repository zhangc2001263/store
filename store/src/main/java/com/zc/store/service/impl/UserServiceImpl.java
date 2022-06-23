package com.zc.store.service.impl;

import com.sun.jmx.snmp.UserAcl;
import com.zc.store.entity.User;
import com.zc.store.mapper.UserMapper;
import com.zc.store.service.IUserService;
import com.zc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块的业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        //先获取user的用户名
        String username = user.getUsername();
        //根据获取的用户名去数据库中查找对应的user用户
        User result = userMapper.queryUserByUsername(username);
        //如果user不为空说明已有这个用户，那这个用户名就不能再被创建了
        if (result != null) {
            throw new UsernameDuplicatedException("用户名已存在");
        }


        // 密码加密处理的实现：md5算法
        // (串 + password + 串)   md5算法进行加密，连续加载三次
        // 盐值 + password + 盐值   盐值就是一个随机的字符串
        //  没有处理过的密码，原来的密码
        String oldPassword = user.getPassword();
        //  用java中的UUID类生成一串随机字符，转换为字符串，全部转换为大写
        String salt = UUID.randomUUID().toString().toUpperCase();
        //一定要将生成的盐值存到数据库中
        user.setSalt(salt);
        //加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密后的密码再放到user对象中
        user.setPassword(md5Password);
        /**
         * 补全信息：
         *    setIsDelete：用户刚被创建是没有被删除的，所以将这个属性设置为0表示未被删除
         *    setCreatedUser：设置创建用户的用户名
         *    setModifiedUser：修改这个用户的用户就是当前创建这个用户的用户
         *    setCreatedTime：设置创建时间
         *    setModifiedTime：设置修改时间，当前是创建用户的方法，所有修改时间和创建时间一样
         */
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(new Date());
        user.setModifiedTime(new Date());


        Integer rows = userMapper.insertUser(user);
        //如果返回值不为1，说明插入失败，有可能是正在插入的过程中发生了服务器或者数据库系统宕机
        if (rows != 1) {
            throw new InsertException("在用户创建的过程中产生了未知异常");
        }
    }

    @Override
    public User login(String username, String password) {
        //根据输入的登录用户名查询用户
        User user = userMapper.queryUserByUsername(username);
        //取出这个用户上一次放入到数据库中的盐值
        String salt = user.getSalt();
        //将用户输入的密码进行和注册时的密码相同的md5算法加密
        String newMd5Password = getMD5Password(password, salt);
        //根据用户名查询用户是否存在，不存在则抛出异常
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        } else if (user.getIsDelete() == 1) {
            throw new UserNotFoundException("该用户已被删除");
        } else if (!newMd5Password.equals(user.getPassword())) {
            throw new PasswordNotMatchException("密码错误");
        }

        //登录后，前端页面登录那一块儿会展示出登录用户的用户名和头像，这两个字段和id匹配，可以只将这三个数据返回给调用者，提升效率
        User user1 = new User();
        user1.setUid(user.getUid());
        user1.setUsername(user.getUsername());
        user1.setAvatar(user.getAvatar());
        //执行到这就是用户正常登录
        return user;
    }

    @Override
    public void updateUserPassword(String username, String oldPassword, String newPassword) {
        //根据用户名找出目标用户
        User user = userMapper.queryUserByUsername(username);
        //取出当前用户的盐值
        String salt = user.getSalt();
        //将输入的旧密码和当前用户的盐值进行加密用来验证用户输入的旧密码是否正确
        String oldMd5Password = getMD5Password(oldPassword, salt);
        if (!oldMd5Password.equals(user.getPassword())) {
            throw new PasswordNotMatchException("密码错误");
        }
        //用旧密码的盐值来对新密码加密
        String newMd5Password = getMD5Password(newPassword, salt);

        Integer res = userMapper.updateUserPassword(username, newMd5Password, username, new Date());
        if (res != 1) {
            throw new InsertException("在修改密码的过程中产生了未知异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("没有找到该用户");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void updateUserInfo(Integer uid, String username, User user) {
        User result = userMapper.findByid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("没有找到该用户");
        }

        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer integer = userMapper.updateUserInfo(user);
        if (integer != 1) {
            throw new UpdateException("更新信息时发生未知错误");
        }
    }

    /**
     * 修改头像
     * @param uid 当前用户的id
     * @param username 当前用户的用户名
     * @param avatar 要修改的目标头像的新的路径
     */
    @Override
    public void updateAvatarByUid(Integer uid, String username, String avatar) {
        User result = userMapper.findByid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("没有找到该用户");
        }

        Integer integer = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (integer != 1) {
            throw new UpdateException("更新头像时产生异常");
        }

    }


    //MD5算法给密码加密
    private String getMD5Password(String password, String salt) {

        //进行三次加密
        for (int i = 0; i < 3; i++) {
            /**
             * DigestUtils.md5DigestAsHex：md5加密算法
             * 加密规则是盐值+密码+盐值，这个方法传入值只能是一个流或者字节，getBytes()将其转换为字节，在转换为全大写
             */
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        return password;
    }


}
