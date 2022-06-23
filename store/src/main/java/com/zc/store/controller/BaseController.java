package com.zc.store.controller;

import com.zc.store.controller.ex.*;
import com.zc.store.service.ex.*;
import com.zc.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    public static final int OK = 200;

    /**
     * ExceptionHandler注解的作用是统一异常处理，只要是ServiceException类型的异常全部到这来处理
     * 自动将异常对象传递到此方法的参数列表上
     * 项目中产生了异常，被统一拦截到这，这个方法此时就充当的是请求处理方法，方法的返回值直接给到前端
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult handleException(Throwable e) {
        JsonResult jsonResult = new JsonResult(e);
        if (e instanceof UsernameDuplicatedException) {
            jsonResult.setState(4000);
            jsonResult.setMessage("用户名被注册");
        } else if (e instanceof AddressCountLimitException) {
            jsonResult.setState(4001);
            jsonResult.setMessage("用户的收货地址超出上限");
        } else if (e instanceof AddressNotFoundException) {
            jsonResult.setState(4002);
            jsonResult.setMessage("用户的收货地址数据不存在");
        } else if (e instanceof AccessDeniedException) {
            jsonResult.setState(4003);
            jsonResult.setMessage("收货地址数据非法访问异常");
        } else if (e instanceof UserNotFoundException) {
            jsonResult.setState(4004);
            jsonResult.setMessage("用户名没有找到");
        } else if (e instanceof ProductNotFoundException) {
            jsonResult.setState(4005);
            jsonResult.setMessage("产品未找到");
        } else if (e instanceof CartNotFoundException) {
            jsonResult.setState(4006);
            jsonResult.setMessage("购物车信息未找到");
        } else if (e instanceof InsertException) {
            jsonResult.setState(5000);
            jsonResult.setMessage("添加信息时出现异常");
        } else if (e instanceof DeleteException) {
            jsonResult.setState(5001);
            jsonResult.setMessage("删除数据时发生未知异常");
        } else if (e instanceof PasswordNotMatchException) {
            jsonResult.setState(5002);
            jsonResult.setMessage("密码错误");
        } else if (e instanceof UpdateException) {
            jsonResult.setState(5003);
            jsonResult.setMessage("更新数据时产生未知异常");
        } else if (e instanceof FileEmptyException) {
            jsonResult.setState(6000);
            jsonResult.setMessage("文件为空");
        } else if (e instanceof FileSizeException) {
            jsonResult.setState(6001);
            jsonResult.setMessage("文件大小异常");
        } else if (e instanceof FileStateException) {
            jsonResult.setState(6002);
            jsonResult.setMessage("文件状态异常");
        } else if (e instanceof FileTypeException) {
            jsonResult.setState(6003);
            jsonResult.setMessage("文件类型异常");
        } else if (e instanceof FileUploadIOException) {
            jsonResult.setState(6004);
            jsonResult.setMessage("文件读写异常");
        }
        return jsonResult;
    }

    /**
     * 从HttpSession对象中获取uid
     *
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     *
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

    // 欧几里得算法求两个数的最大公因数
    public static int fun1(int a, int b) {
        return b == 0 ? a : fun1(b, a % b);
    }
}
