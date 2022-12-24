package com.example.todolist.service;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Picture;
import com.example.todolist.pojo.Task;
import com.example.todolist.pojo.TaskPicture;
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

    /*
    * 增加任务
    * */
    public boolean addTask(Task task);

    /*
    * 通过id获取任务
    * */
    public Task getTaskById(Integer id);

    /*
    * 获取指定分区所有任务
    * */
    public List<Task> getAreaTaskList(Integer id);

    /*
    * 根据分区id和任务等级获取所有任务
    * */
    public List<Task> getTaskByAreaAndLevel(Integer areaId, Integer level);

    /*
     * 获取指定父任务id的所有子任务数量
     * */
    public Integer getSubTaskCount(Integer id);

    /*
    * 获取指定父任务id的所有子任务
    * */
    public List<Task> getAllSubTasks(Integer id);

    /*
     * 获取指定任务id的所有备注图片
     * */
    public List<TaskPicture> getAllTaskPicture(Integer taskId);

    /*
     * 更新任务内容
     * */
    public boolean alterTaskContentById(String content, List<String> taskPictureSrcList, Integer taskId);

    /*
    * 刷新数据库
    * */
    public boolean refreshTask(Integer userId);
}
