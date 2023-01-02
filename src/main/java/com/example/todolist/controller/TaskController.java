package com.example.todolist.controller;

import com.example.todolist.common.RetJson;
import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import com.example.todolist.pojo.User;
import com.example.todolist.pojo.UserInfo;
import com.example.todolist.service.TaskService;
import com.example.todolist.utils.CopyFieldValue;
import com.example.todolist.vo.TaskContent;
import com.example.todolist.vo.TaskPictureContent;
import lombok.val;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
        taskService.refreshTask(id);
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
    public RetJson addTask(@Param("level") Integer level,@Param("superId") Integer superId,
                           @Param("beginTime") String beginTime,@Param("endTime") String endTime,
                           @Param("finishRate") Integer finishRate,@Param("title") String title,
                           @Param("areaId") Integer areaId){
        Task task=new Task();
        task.setAreaId(areaId);
        task.setTitle(title);
        task.setFinishRate(finishRate);
        task.setSuperId(superId);
        task.setLevel(level);
        DateFormat cstFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat gmtFormate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            Date date1=cstFormate.parse(beginTime);
            Date date2=cstFormate.parse(endTime);
            System.out.println(date1);
            task.setBeginTime(date1);
            task.setEndTime(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer id = user.getId();
        System.out.println(id);
        task.setUserId(id);
        System.out.println(task.toString());
        if(taskService.getTaskByTitle(task.getTitle())!=null){
            return RetJson.fail(-1, "任务已存在");
        }else if(taskService.addTask(task)){
            return RetJson.success(0, "添加成功");
        }
        return RetJson.fail(-2, "添加失败");
    }

    @PostMapping("/getAreaTask")
    public RetJson getAreaTask(@Param("areaId") Integer areaId){
        Integer id = user.getId();
        taskService.refreshTask(id);
        System.out.println(id);
//        Map taskRateList=new HashMap();
        List<Map> taskRateList =new ArrayList<>();
        for(int i=3;i>0;i--){
            Map taskRateListItem=new HashMap();
            Integer cplNum=0;
            Integer totalNum=0;
//            Map taskList=new HashMap();
            List<Map> taskList= new ArrayList<>();
            List<Task> areaTaskList = taskService.getTaskByAreaAndLevel(areaId,i);
            areaTaskList.sort((o1, o2) -> {
                long t1=o1.getEndTime().getTime()-o2.getEndTime().getTime();
                if(t1>0){
                    return 1;
                }else if(t1==0){
                    return 0;
                }else{
                    return -1;
                }
            });
//            System.out.println(rateNum);
            /*该分区该优先级下所有任务*/
            int index=0;
            for(Task task:areaTaskList){
                Map taskListItem=new HashMap();
                if(task.getState()==1){
                    /*所有已完成任务,任务有效,为父任务*/
                    if(task.getFinishRate()==100&&task.getState()==1&&task.getSuperId()==0) {
                        cplNum = cplNum + 1;
                    }
                    /*该优先级下有效任务总数，父任务*/
                    if(task.getState()==1&&task.getSuperId()==0){
                        totalNum=totalNum+1;
                    }
                    int compare = task.getEndTime().compareTo(new Date());
                    taskListItem.put("id",task.getId());
                    taskListItem.put("rate",task.getLevel());
                    /*截至时间早于今天，且未完成*/
                    if(task.getFinishRate()!=100&&compare<0){
                        /*计算距今天数*/
                        Date endTime =task.getEndTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        final String formatStr = format.format(endTime);
                        LocalDate target = LocalDate.parse(formatStr);
                        LocalDate today = LocalDate.now();
                        long days = Math.abs(target.toEpochDay() - today.toEpochDay());
                        taskListItem.put("restDay",days);
                    }

                    taskListItem.put("finishRate",task.getFinishRate());
                    /*toEndTime：距截止时间*/
                    long mills1 = task.getEndTime().getTime();//截至时间，时间戳
                    long mills2 = new Date().getTime();//现在时间戳
                    long distanceMillis = mills1 - mills2;//相距毫秒数
                    if(distanceMillis<=0){
                        taskListItem.put("toEndTime","任务已截止");
                    }else if(distanceMillis/(1000)<60){
                        taskListItem.put("toEndTime","少于1分钟");
                    }else if(distanceMillis/(1000*60)<60){
                        long minutes = distanceMillis/(1000*60);
                        taskListItem.put("toEndTime","剩余"+minutes+"分钟");
                    }else if(distanceMillis/(1000*60*60)<24){
                        long minutes=distanceMillis/(1000*60);
                        long hours = minutes/60;
                        long restMinutes=minutes%60;
                        taskListItem.put("toEndTime","剩余"+hours+"小时"+restMinutes+"分钟");
                    }else if(distanceMillis/(1000*60*60*24)<7){
                        long hours=distanceMillis/(1000*60*60);
                        long days=hours/24;
                        long restHours=hours%24;
                        taskListItem.put("toEndTime","剩余"+days+"天"+restHours+"小时");
                    }else{
                        long days=distanceMillis/(1000*60*60*24);
                        taskListItem.put("toEndTime","剩余"+days+"天");
                    }

                    taskListItem.put("parentId",task.getSuperId());
                    taskListItem.put("title",task.getTitle());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    taskListItem.put("begin",format.format(task.getBeginTime()));
                    taskListItem.put("end",format.format(task.getEndTime()));
                    /*获取子任务数*/
                    final Integer subTaskCount = taskService.getSubTaskCount(task.getId());
                    if(subTaskCount>0){
                        final List<Task> allSubTasks = taskService.getAllSubTasks(task.getId());
                        allSubTasks.sort(new Comparator<Task>() {
                            @Override
                            public int compare(Task o1, Task o2) {
                                long t1=o1.getEndTime().getTime()-o2.getEndTime().getTime();
                                if(t1>0){
                                    return 1;
                                }else if(t1==0){
                                    return 0;
                                }else{
                                    return -1;
                                }
                            }
                        });
                        /*获取完成子任务数、所有子任务数*/
                        int subFinish=0;
                        int allTask=0;
                        for(Task task1 : allSubTasks){
                            /*该子任务完成*/
                            if(task1.getFinishRate()==100&&task1.getState()==1){
                                subFinish++;
                            }
                            /*该子任务未完成、未截至*/
    //                        System.out.println(task1.getEndTime().compareTo(new Date()));
                            if(task1.getState()==1){
                                allTask++;
                            }
                        }
                        taskListItem.put("isParent",true);
                        taskListItem.put("cplSonNum",subFinish);
                        taskListItem.put("totalSonNum",allTask);
                    }else{
                        taskListItem.put("isParent",false);
                        taskListItem.put("cplSonNum",0);
                        taskListItem.put("totalSonNum",0);
                    }
                    taskList.add(taskListItem);
                    index++;
                }
            }
            taskRateListItem.put("cplNum",cplNum);
            taskRateListItem.put("totalNum",totalNum);
            taskRateListItem.put("taskList",taskList);
            taskRateList.add(taskRateListItem);
        }
        Map map=new HashMap();
        map.put("taskList",taskRateList);
        map.put("listLength",taskRateList.size());
        return RetJson.success(0,"获取信息成功",map);
    }

    @GetMapping("/getTaskContent")
    public RetJson getTaskContent(@Param("taskId") Integer taskId){
        if (taskService.getTaskById(taskId) == null){
            return RetJson.fail(-1, "查询失败");
        }
        Task task = taskService.getTaskById(taskId);
        TaskContent taskContent = new TaskContent(taskId, task.getContent(), taskService.getAllTaskPicture(taskId));
        return RetJson.success("taskContent", taskContent);
    }

    @PutMapping("updateTaskContent")
    public RetJson updateTaskContent(@RequestBody TaskPictureContent taskContent){
        System.out.println(taskContent);
        if (taskService.alterTaskContentById(taskContent.getContent(), taskContent.getTaskPictureList(), taskContent.getTaskId())){
            return RetJson.success(0, "更新成功");
        }
        return RetJson.fail(-1,"更新失败");
    }
}
