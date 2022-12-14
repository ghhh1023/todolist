package com.example.todolist.service;


import com.example.todolist.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public Boolean login(String userName, String password);

    public User findUserByUserName(String userName);

    public User getUserByUserName(String userName);

    public Integer[] getAllUserIdList();

    public Boolean logout();

    public void register(User user);

    ;


    User getUserByUserId(Integer valueOf);


}
