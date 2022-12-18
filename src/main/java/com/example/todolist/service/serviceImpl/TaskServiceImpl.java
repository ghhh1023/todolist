package com.example.todolist.service.serviceImpl;

import com.example.todolist.mapper.AreaMapper;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    AreaMapper areaMapper;

    // 通过用户id查询任务分类，再通过分类查询任务
    @Override
    public Map<String, List<Task>> getTaskViaArea(Integer user_id) {
        List<Area> areas = areaMapper.getAreaByUserId(user_id);
        Map map = new HashMap();
        for (Area area : areas){
            System.out.println();
            map.put(area.getAreaName(),taskMapper.getTaskByAreaId(area.getAreaId()));
        }
        return map;
    }

    @Override
    public Integer getDoneTaskCount(Integer id) {
        return taskMapper.getDoneTaskCount(id);
    }

    @Override
    public Boolean addArea(Area area) {
        return areaMapper.insertArea(area);
    }
}
