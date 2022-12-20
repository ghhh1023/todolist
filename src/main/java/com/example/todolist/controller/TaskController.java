package com.example.todolist.controller;

import com.example.todolist.common.RetJson;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @GetMapping("/getTaskNum")
    public RetJson getTaskNum(){
        Integer id = user.getId();
        Map map = new HashMap();
        map.put("taskAreaNum", taskService.getAreaCount(id));
        map.put("taskAreaList", taskService.getTaskNum(id));
        return RetJson.success(0, "查询成功", map);
    }

    @PostMapping("/addArea")
    public RetJson addArea(@RequestBody Area area){
        Integer id = user.getId();
        area.setUserId(id);
        if (taskService.getAreaByNameAndId(area.getAreaName(), id) != null){
            return RetJson.fail(-1, "分区已存在");
        }
        taskService.addArea(area);
        return RetJson.success(0, "增加成功");
    }
}
