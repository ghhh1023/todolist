package com.example.todolist.service;


import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public Boolean login(String userName, String password);

    public User findUserByUserName(String userName);

    public User getUserByUserName(String userName);

    public Integer[] getAllUserIdList();

    public Boolean logout();

    public void register(User user);

    public UserInfo getUserInfo(Integer id);

    public boolean alterUserInfo(UserInfo userInfo);

    public boolean alterPictureId(Integer pictureId,Integer id);

    User getUserByUserId(Integer valueOf);


}
