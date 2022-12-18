package com.example.todolist.service;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {
    public Map<String, List<Task>> getTaskViaArea(Integer id);

    public Integer getDoneTaskCount(Integer id);

    public Boolean addArea(Area area);
}
