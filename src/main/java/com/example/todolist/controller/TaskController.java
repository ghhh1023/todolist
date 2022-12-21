package com.example.todolist.controller;

import com.example.todolist.common.RetJson;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.TaskService;
import com.example.todolist.utils.CopyFieldValue;
import lombok.val;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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

    @DeleteMapping("/deleteArea/{areaId}")
    public RetJson deleteArea(@PathVariable("areaId") Integer areaId){
        System.out.println(areaId);
        Integer id = user.getId();
        if (taskService.deleteArea(areaId, id)){
            return RetJson.success(0, "删除成功");
        }
        return RetJson.fail(-1, "分区不存在，删除失败");
    }

    @PutMapping("/updateArea")
    public RetJson updateArea(@RequestBody Area area){
        Integer id = user.getId();
        if (taskService.getAreaById(area.getAreaId()) == null){
            return RetJson.fail(-2, "更新失败");
        }
        if (taskService.getAreaByNameAndId(area.getAreaName(),id) != null){
            return RetJson.fail(-1, "分区名已存在");
        }
        Area pastArea = taskService.getAreaById(area.getAreaId());
        CopyFieldValue.copyFieldValue(area, pastArea);
        if (taskService.updateArea(area)){
            return RetJson.success(0, "更新成功");
        }
        return RetJson.fail(-2, "更新失败");
    }

    @PostMapping("/insertTask")
    public RetJson addTask(@RequestBody Task task){
        Integer id = user.getId();
        System.out.println(id);
        if(taskService.getTaskById(task.getId())!=null){
            return RetJson.fail(-1, "任务已存在");
        }else if(taskService.addTask(task)){
            return RetJson.success(0, "添加成功");
        }
        return RetJson.fail(-2, "添加失败");
    }

}
