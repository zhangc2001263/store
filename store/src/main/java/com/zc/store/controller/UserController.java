package com.zc.store.controller;

import com.zc.store.controller.ex.*;
import com.zc.store.entity.User;
import com.zc.store.service.IUserService;

import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")  //要访问这个类下所有方法，路径必须有/users
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @RequestMapping("/insertuser")
    public JsonResult insertUser(User user) {
        userService.insertUser(user);

        return new JsonResult(OK);
    }
    /*
    @RequestMapping("/insertuser")
    public JsonResult insertUser(User user) {
        //设置响应信息
        JsonResult jsonResult = new JsonResult();
        try {
            //调用service层插入用户信息
            userService.insertUser(user);
            //插入成功设置状态码为200
            jsonResult.setState(200);
            jsonResult.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            //用户名被占用异常
            jsonResult.setState(4000);
            jsonResult.setMessage("用户名被占用");
        } catch (InsertException e) {
            //注册时异常，可能是系统宕机异常
            jsonResult.setState(5000);
            jsonResult.setMessage("注册时产生未知异常");
        }
        return jsonResult;
    }
     */

    @RequestMapping("/login")
    public JsonResult login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        return new JsonResult(OK, user);
    }

    @RequestMapping("/exit")
    public void exit(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/web/login.html");
    }
    @RequestMapping("/update")
    public JsonResult update(String username, String oldPassword, String newPassword, HttpSession session) {
        String usernameFromSession = getUsernameFromSession(session);

        userService.updateUserPassword(usernameFromSession, oldPassword, newPassword);

        return new JsonResult(OK);
    }

    @RequestMapping("/getbyuid")
    public JsonResult getById(HttpSession session) {
        //根据session中的uid查找用户
        User user = userService.getByUid(getUidFromSession(session));
        return new JsonResult(OK, user);
    }

    @RequestMapping("/updateuserinfo")
    public JsonResult updateUserInfo(User user, HttpSession session) {

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.updateUserInfo(uid, username, user);
        return new JsonResult(OK);
    }

    //设置上传的头像的最大值为10mb
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/jpg");
    }

    /**
     * MultipartFile接口是springmvc提供的一个接口，这个接口包装了获取文件类型的数据(任何类型的file都可以接收)
     * springboot整合了springmvc，只需要在参数列表上声明一个MultipartFile类型的参数即可
     *
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("/updateAvatarByUid")
    public JsonResult updateAvatarByUid(HttpSession session, MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        } else if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        } else if (!AVATAR_TYPE.contains(file.getContentType())) {
            throw new FileTypeException("文件类型异常");
        }
        //将头像文件放在本项目部署目录的upload目录下，parent是upload的真实路径
        String parent = session.getServletContext().getRealPath("upload");
        //File对象指向这个路径
        File dir = new File(parent);
        //判断这个路径是否存在
        if (!dir.exists()) {
            dir.mkdirs();  //创建当前目录
        }

        //获取到这个文件名称，UUID工具来生成一个新的字符串作为文件名
        //获取文件的名称
        String originalFilename = file.getOriginalFilename();
        //获取文件名称中"."的下表索引
        int index = originalFilename.lastIndexOf(".");
        //从"."开始截取到最后，获取这个文件的后缀
        String substring = originalFilename.substring(index);
        //用UUID生成一串字符串作为文件的名字
        String filename = UUID.randomUUID().toString().toUpperCase() + substring;

        //创建一个和上面的文件同名的空文件
        File dest = new File(dir, filename);
        //transferTo将参数file中的数据传入dest中
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写时发生未知异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //头像的路径
        String avatar = "/upload/" + filename;
        userService.updateAvatarByUid(uid, username, avatar);
        //将头像的路径返回给前端，将来用于头像展示使用
        return new JsonResult(OK,avatar);
    }
}
