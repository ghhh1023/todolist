package com.example.todolist.controller;


import com.example.todolist.common.RetJson;
import com.example.todolist.config.Client;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.RedisService;
import com.example.todolist.service.UserService;
import com.example.todolist.utils.GenerateVerificationCode;
import com.example.todolist.utils.JwtUtils;
import com.example.todolist.utils.MobileMessageUtil;
import com.example.todolist.utils.ValidatedUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author gh
 * 与用户操作有关的控制器,如登入注册等
 */
@RestController
@Validated
public class UserController {
    private static final Integer MAX_SIZE = 5 * 1024 * 1024;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    //登入
    @PostMapping("/login")
    public RetJson login(@RequestBody User user, HttpServletRequest request) {
        if (!ValidatedUtil.validate(user)) {
            return RetJson.fail(-1, "登录失败，请检查用户名或密码！");
        }
        Boolean b = userService.login(user.getUserName(), user.getPassword());
        if (b == true) {
            user = userService.getUserByUserName(user.getUserName());
            request.setAttribute("id", user.getId() + "");
            //登入成功,则发放token
            if (true) {
                try {
                    //生成一个随机的不重复的uuid
                    UUID uuid = UUID.randomUUID();
                    request.setAttribute("uuid", uuid.toString());
                    String token = JwtUtils.createToken(uuid, user.getId().toString());
                    //将uuid和user以键值对的形式存放在redis中
                    user.setPassword(null);
                    user.setSalt(null);
                    redisService.set("user:" + user.getId(), uuid.toString(), 60 * 60 * 24 * 7);

                    Map map = new LinkedHashMap();
                    map.put("token", token);
                    map.put("id", user.getId());
                    return RetJson.success(0,"登录成功",map);
                } catch (Exception e) {
                    return RetJson.fail(-1, "登录失败,请检查用户名或密码");
                }
            }
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", user.getId());
            return RetJson.success(map);
        } else {
            return RetJson.fail(-1, "登录失败,请检查用户名或密码");
        }
    }

    //获取手机验证码
    /*
     如果手机号长度不合要求返回提示信息还没写
     */

    @RequestMapping("/getCode")
    public RetJson sendIdentifyingCode(@NotNull String phoneNumber) {
        if (!ValidatedUtil.isMobile(phoneNumber)) {
            return RetJson.fail(-2, "手机号码不合法");
        }
        if ((userService.findUserByUserName(phoneNumber) != null)) {
            return RetJson.fail(-1, "该用户已经注册");
        }
        String verificationCode = GenerateVerificationCode.generateVerificationCode(4);
        Client.Request request = null;
        Client client = new Client();
        request = MobileMessageUtil.sendIdentifyingCode(phoneNumber, verificationCode);
        //在redis中存入用户的账号和对应的验证码
        redisService.set(phoneNumber, verificationCode, 300);
        return RetJson.success(0, "验证码已发送");
    }

    /**
     * 注册
     * //     * @param user
     * //     * @param code
     *
     * @return
     */
    @RequestMapping("/register")
    public RetJson userRegister( String userName, @NotNull
    @Length(max = 16, min = 6, message = "密码不合法") String password, String code) {
        if (!ValidatedUtil.isMobile(userName)) {
            return RetJson.fail(-2, "手机号码不合法");
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        if (redisService.exists(user.getUserName()) && redisService.get(user.getUserName()).equals(code)) {
            if (userService.findUserByUserName(user.getUserName()) == null) {
                userService.register(user);
                return RetJson.success(0, "注册成功");
            }
            return RetJson.fail(-2, "用户已存在！");
        }
        return RetJson.fail(-1, "验证码不正确！");
    }

    /**
     * 获取用户信息
     */
    @RequestMapping("/getUserInfo")
    public RetJson getUserInfo(HttpServletRequest request){
        Integer id = ((User)request.getAttribute("user")).getId();
        UserInfo userInfo=userService.getUserInfo(id);
        if (userInfo==null){
            return RetJson.fail(-1,"获取用户信息失败");
        }else{

            return RetJson.success("userInfo",userInfo);
        }
    }
    /**
     * 修改用户信息
     * @param userInfo 用户信息，字段为：
     * @param request
     * @return
     */
    @PostMapping("/alterUserInfo")
    public RetJson alterUserInfo(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if (!ValidatedUtil.validate(userInfo)){
            return RetJson.fail(-1,"请检查参数");
        }
        Integer id = ((User)request.getAttribute("user")).getId();
        userInfo.setId(id);
        UserInfo pastUserInfo = userService.getUserInfo(id);
        copyFieldValue(userInfo,pastUserInfo);
        userService.alterUserInfo(userInfo);
        return RetJson.success(0,"修改成功");
    }

    private void copyFieldValue(UserInfo userInfo,UserInfo pastUserInfo){
        for(Field f : userInfo.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                if(f.get(userInfo) == null&&f.get(pastUserInfo) != null){
                    f.set(userInfo,f.get(pastUserInfo));
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }

}
