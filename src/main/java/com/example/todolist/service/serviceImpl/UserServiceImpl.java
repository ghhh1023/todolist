package com.example.todolist.service.serviceImpl;




import com.example.todolist.mapper.UserInfoMapper;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class UserServiceImpl implements UserService {

    final char[] codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
    final int SALT_LENGTH = 8;//盐值长度
    final int ENCRYPT_NUM = 1024;//加密次数

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public Boolean login(String userName, String password) {

        Subject currentUser = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            currentUser.login(token);//登入验证
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public Integer[] getAllUserIdList() {
        Integer[] integers = userMapper.getAllUserIdList();
        return integers;
    }


    @Override
    public void register(User user) {
        String password = user.getPassword();
        String salt = produceSalt();//生成八位的盐值
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        SimpleHash simpleHash = new SimpleHash("md5", password, byteSource, ENCRYPT_NUM);
        user.setPassword(simpleHash.toHex());
        user.setSalt(salt);
        userMapper.register(user);
    }


    @Override
    public User getUserByUserId(Integer valueOf) {
        return userMapper.getUserByUserId(valueOf);
    }


    public String produceSalt() {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SALT_LENGTH; i++) {
            String strRand = null;
            strRand = String.valueOf(codeSequence[random.nextInt(62)]);
            randomString.append(strRand);
        }
        return randomString.toString();
    }

    @Override
    public UserInfo getUserInfo(Integer id) {
        return userInfoMapper.getUserInfoById(id);
    }

    @Override
    public boolean alterUserInfo(UserInfo userInfo) {
        return userInfoMapper.alterUserInfo(userInfo);
    }

}
