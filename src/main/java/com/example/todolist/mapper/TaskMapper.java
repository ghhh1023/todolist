package com.example.todolist.mapper;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TaskMapper {

    /*
     * 获取所有任务对象
     * */
    @Select("select * from task")
    public List<Task> getAllTasks();

    /*
    * 根据任务标题返回任务对象
    * */
    @Select("select * from task where title like #{title}")
    public List<Task> getTaskByTitle(@Param("title") String title);

    /*
    * 根据任务id返回任务对象
    * */
    @Select("select * from task where id=#{id}")
    public List<Task> getTaskById(@Param("id") Integer id);

    /*
    * 获取指定父任务id的所有子任务
    * */
    @Select("select * from task where super_id=#{super_id}")
    public List<Task> getAllSubTasks(@Param("super_id") Integer super_id);

    /*
     * 查询指定分区指定任务等级的任务数量
     * */
    @Select("select count(*) from task where area_id=#{area_id} and level=#{level}")
    public Integer getTaskLevelCountOfArea(@Param("area_id") Integer area_id,
                                           @Param("level") Integer level);

    /*
    * 新增任务
    * */
    @Insert({"insert into task(id,title,user_id,content_id,area_id,super_id,level,begin_time,end_time,state,finish_rate) values(#{id},#{title},#{user_id},#{content_id},#{area_id},#{super_id},#{level},#{begin_time},#{end_time},#{state},#{finish_rate})"})
    public boolean insertTask(Task task);

    /*
    * 通过任务id删除任务
    * */
    @Delete({"delete from task where id=#{id}"})
    public boolean deleteTaskById(@Param("id") Integer id);

    /*
     * 通过任务标题删除任务
     * */
    @Delete({"delete from task where title like #{title}"})
    public boolean deleteTaskByTitle(@Param("title") Integer title);

    /*
     * 通过任务id修改任务标题
     * */
    @Update("update task set title = #{new_title} where id=#{id}")
    public boolean alterTaskTitleById(@Param("new_title") String new_title,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务标题
     * */
    @Update("update task set title = #{new_title} where title=#{old_title}")
    public boolean alterTaskTitleByTitle(@Param("new_title") String new_title,
                                      @Param("old_title") String old_title);

    /*
     * 通过任务id修改任务级别
     * */
    @Update("update task set level = #{new_level} where id=#{id}")
    public boolean alterTaskLevelById(@Param("new_level") Integer new_level,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务级别
     * */
    @Update("update task set level = #{new_level} where title=#{title}")
    public boolean alterTaskLevelByTitle(@Param("new_title") Integer new_level,
                                        @Param("title") String title);
    /*
     * 通过任务id修改任务截至时间
     * */
    @Update("update task set end_time = #{new_time} where id=#{id}")
    public boolean alterTaskEndTimeById(@Param("new_time") Date new_time,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务截至时间
     * */
    @Update("update task set end_time = #{new_time} where title=#{title}")
    public boolean alterTaskEndTimeByTitle(@Param("new_time") Date new_time,
                                         @Param("title") String title);
    /*
     * 通过任务id修改任务分区
     * */
    @Update("update task set area_id = #{new_area} where id=#{id}")
    public boolean alterTaskAreaById(@Param("new_area") Integer area_id,
                                        @Param("id") Integer id);

    /*
     * 通过任务标题修改任务分区
     * */
    @Update("update task set area_id = #{new_area} where title=#{title}")
    public boolean alterTaskAreaByTitle(@Param("new_area") Integer area_id,
                                           @Param("title") String title);

    /*
    * 通过标题更新整条任务
    * */
//    @Update("update task set area_id = #{new_area}, where title=#{title}")
//    public boolean alterTaskById(@Param("new_area") Task task,
//                                        @Param("title") String title);
}
