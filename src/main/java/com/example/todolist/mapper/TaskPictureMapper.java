package com.example.todolist.mapper;


import com.example.todolist.pojo.Picture;
import com.example.todolist.pojo.Task;
import com.example.todolist.pojo.TaskPicture;
import com.example.todolist.pojo.Text;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskPictureMapper {
    @Select("select * from task_picture where task_id=#{taskId}")
    public List<TaskPicture> getPictureByTaskId(@Param("taskId") Integer taskId);

    @Update({"update task_picture set picture_src=#{pictureSrc} where id=#{id}"})
    public boolean alterTaskPicture(TaskPicture taskPicture);

    @Insert("insert into task_picture(task_id,picture_src) values (#{taskId},#{pictureSrc})")
    public boolean insertTaskPicture(TaskPicture taskPicture);


    /*
     * 通过任务id 删除图片
     * */
    @Delete("delete from task_picture where task_id=#{taskId}")
    public boolean deleteTaskPicture(@Param("taskId") Integer taskId);
}
