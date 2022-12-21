package com.example.todolist.service;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.vo.TaskAreaList;

import java.util.List;
import java.util.Map;

public interface TaskService {
    public Map<String, List<Task>> getTaskViaArea(Integer id);

    public Integer getDoneTaskCount(Integer id);

    public Boolean addArea(Area area);

    public Area getAreaByNameAndId(String areaName, Integer userId);

    public Area getAreaById(Integer areaId);

    public List<TaskAreaList> getTaskNum(Integer id);

    public Integer getAreaCount(Integer userId);

    public Boolean deleteArea(Integer areaId, Integer userId);

    public Boolean updateArea(Area area);
}
