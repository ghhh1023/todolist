package com.example.todolist.service.serviceImpl;

import com.example.todolist.mapper.AreaMapper;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.mapper.TaskPictureMapper;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.pojo.TaskPicture;
import com.example.todolist.service.TaskService;
import com.example.todolist.vo.TaskAreaList;
import com.example.todolist.vo.TaskPictureContent;
import lombok.Data;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    TaskPictureMapper taskPictureMapper;

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

    @Override
    public List<Task> getAreaTaskList(Integer id) {
        return taskMapper.getTaskByAreaId(id);
    }

    @Override
    public List<Task> getTaskByAreaAndLevel(Integer areaId, Integer level) {
        return taskMapper.getTaskByAreaAndLevel(areaId, level);
    }

    @Override
    public Integer getSubTaskCount(Integer id) {
        return taskMapper.getSubTasksCount(id);
    }

    @Override
    public List<Task> getAllSubTasks(Integer id) {
        return taskMapper.getAllSubTasks(id);
    }

    @Override
    public List<TaskPicture> getAllTaskPicture(Integer taskId) {
        return taskPictureMapper.getPictureByTaskId(taskId);
    }

    @Override
    @Transactional
    public boolean alterTaskContentById(String content, List<String> taskPictureSrcList, Integer taskId) {
        boolean flag1 = taskMapper.alterTaskContentById(content, taskId);
        boolean flag2 = taskPictureMapper.deleteTaskPicture(taskId);
        if (taskPictureMapper.getPictureByTaskId(taskId).isEmpty()){
            flag2 = true;
        }
        boolean flag3 = true;
        TaskPicture taskPicture = new TaskPicture();
        for(String pictureSrc : taskPictureSrcList){
            taskPicture.setTaskId(taskId);
            taskPicture.setPictureSrc(pictureSrc);
            flag3 = taskPictureMapper.insertTaskPicture(taskPicture);
            if (!flag3){
                break;
            }
        }
        return flag1 && flag2 && flag3;
    }

    @Override
    public boolean refreshTask(Integer userId) {
        Integer newState = 0;
        List<Task> allTasks = taskMapper.getAllTasksByUserId(userId);
        for(Task task : allTasks){
            newState = 0;
            final Date endTime = task.getEndTime();
            final Date beginTime = task.getBeginTime();
            long endDay = endTime.getTime()/(1000*60*60*24);
            long beginDay = beginTime.getTime()/(1000*60*60*24);
            long today=new Date().getTime()/(1000*60*60*24);
            /*有效:截至未完成、任务时间与今天交集*/
            if(endDay-today<0&&task.getFinishRate()!=100 ||!(beginDay>today)&&!(endDay<today)){
                newState = 1;
            }
            taskMapper.alterTaskStateById(newState, task.getId());
        }
        return true;
    }

    @Override
    public Task getTaskByTitle(String title) {
        return taskMapper.getTaskByTitle(title);
    }


    @Override
    @Transactional
    public boolean alterTask(Task task) {
        boolean flag1 = taskMapper.alterTask(task);
        boolean flag2 = taskMapper.alterTaskLevel(task.getLevel(), task.getId());
        return flag1 && flag2;
    }

    @Override
    public boolean deleteTask(Integer id, Integer userId) {
        taskMapper.deleteTaskBySuperId(id, userId);
        return taskMapper.deleteTaskById(id, userId);
    }
}
