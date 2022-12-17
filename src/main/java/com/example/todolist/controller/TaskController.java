package com.example.todolist.controller;

import com.example.todolist.common.RetJson;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    User user=null;
    UserInfo userInfo=null;

    @ModelAttribute
    public void common(HttpServletRequest request){
        user=(User)request.getAttribute("user");
        userInfo=(UserInfo)request.getAttribute("userInfo");
    }

    @GetMapping("/getTask")
    public RetJson getTaskViaArea(){
        Integer id = user.getId();
        Map map = taskService.getTaskViaArea(id);
        map.put("已完成任务数", taskService.getDoneTaskCount(id));
        return RetJson.success(0, "查询成功", map);
    }

}
