package com.example.todolist.service.serviceImpl;

import com.example.todolist.mapper.AreaMapper;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.service.TaskService;
import com.example.todolist.vo.TaskAreaList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Area getAreaByNameAndId(String areaName, Integer userId) {
        return areaMapper.getAreaByNameAndId(areaName, userId);
    }

    @Override
    public List<TaskAreaList> getTaskNum(Integer id) {
        List<Area> areas = areaMapper.getAreaByUserId(id);
        List<TaskAreaList> taskAreaLists = new ArrayList<>();
        for (Area area : areas){
            Integer areaId = area.getAreaId();
            Integer cmnTotalNum = taskMapper.getTaskLevelCountOfArea(areaId, 2, 1, 101);
            Integer cmnCplNum = cmnTotalNum - taskMapper.getTaskLevelCountOfArea(areaId, 2, 1, 100);
            Integer emgTotalNum = taskMapper.getTaskLevelCountOfArea(areaId, 3, 1, 101);
            Integer emgCplNum = emgTotalNum - taskMapper.getTaskLevelCountOfArea(areaId, 3, 1, 100);
            TaskAreaList taskAreaList = new TaskAreaList(areaId, area.getAreaName(), emgCplNum, emgTotalNum, cmnCplNum, cmnTotalNum);
            taskAreaLists.add(taskAreaList);
        }
        return taskAreaLists;
    }

    @Override
    public Integer getAreaCount(Integer userId) {
        return areaMapper.getAreaCount(userId);
    }

    @Override
    public Area getAreaById(Integer areaId) {
        return areaMapper.getAreaByAreaId(areaId);
    }

    @Override
    public Boolean deleteArea(Integer areaId, Integer userId) {
        return areaMapper.deleteAreaById(areaId, userId);
    }

    @Override
    public Boolean updateArea(Area area) {
        return areaMapper.alterArea(area);
    }

    @Override
    public boolean addTask(Task task) {
        return taskMapper.insertTask(task);
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskMapper.getTaskById(id);
    }
}
