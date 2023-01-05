package com.example.todolist.mapper;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.Task;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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
    public Task getTaskByTitle(@Param("title") String title);

    /*
    * 根据任务id返回任务对象
    * */
    @Select("select * from task where id=#{id}")
    public Task getTaskById(@Param("id") Integer id);

    /*
     * 根据分区id返回任务对象
     * */
    @Select("select * from task where area_id=#{areaId}")
    public List<Task> getTaskByAreaId(@Param("areaId") Integer areaId);

    /*
    * 获取指定父任务id的所有子任务
    * */
    @Select("select * from task where super_id=#{superId}")
    public List<Task> getAllSubTasks(@Param("superId") Integer superId);

    /*
    * 获取指定父任务id的所有子任务数量
    * */
    @Select("select count(*) from task where super_id=#{superId}")
    public Integer getSubTasksCount(@Param("superId") Integer superId);

    /*
     * 查询指定分区指定任务等级和指定完成度的任务数量
     * */
    @Select("select count(*) from task where area_id=#{areaId} and level=#{level} and state=#{state} and finish_rate <#{finishRate} and super_id = 0")
    public Integer getTaskLevelCountOfArea(@Param("areaId") Integer areaId,
                                           @Param("level") Integer level,
                                           @Param("state") Integer state,
                                           @Param("finishRate") Integer finishRate);

    /*
    * 根据分区id和任务等级获取所有任务
    * */
    @Select("select * from task where area_id=#{areaId} and level=#{level}")
    public List<Task> getTaskByAreaAndLevel(@Param("areaId") Integer areaId,@Param("level") Integer level);

    /*
     * 查询指定分区指定任务等级的任务数量
     * */
    @Select("select count(*) from task where user_id=#{userId} and state=1")
    public Integer getDoneTaskCount(@Param("userId") Integer userId);
    /*
    * 新增任务
    * */
    @Insert({"insert into task(title,user_id,content,area_id,super_id,level,begin_time,end_time,state,finish_rate) values(#{title},#{userId},#{content},#{areaId},#{superId},#{level},#{beginTime},#{endTime},#{state},#{finishRate})"})
    public boolean insertTask(Task task);

    /*
    * 通过任务id删除任务
    * */
    @Delete({"delete from task where id=#{id} and user_id=#{userId}"})
    public boolean deleteTaskById(@Param("id") Integer id, @Param("userId") Integer userId);

    /*
     * 通过父id删除子任务
     * */
    @Delete({"delete from task where super_id=#{superId} and user_id=#{userId}"})
    public boolean deleteTaskBySuperId(@Param("superId") Integer superId, @Param("userId") Integer userId);

    /*
     * 通过任务标题删除任务
     * */
    @Delete({"delete from task where title like #{title}"})
    public boolean deleteTaskByTitle(@Param("title") Integer title);

    /*
     * 通过任务id修改任务标题
     * */
    @Update("update task set title = #{newTitle} where id=#{id}")
    public boolean alterTaskTitleById(@Param("newTitle") String newTitle,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务标题
     * */
    @Update("update task set title = #{newTitle} where title=#{oldTitle}")
    public boolean alterTaskTitleByTitle(@Param("newTitle") String newTitle,
                                      @Param("oldTitle") String oldTitle);

    /*
     * 通过任务id修改任务级别
     * */
    @Update("update task set level = #{newLevel} where id=#{id}")
    public boolean alterTaskLevelById(@Param("newLevel") Integer newLevel,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务级别
     * */
    @Update("update task set level = #{new_level} where title=#{title}")
    public boolean alterTaskLevelByTitle(@Param("newLevel") Integer newLevel,
                                        @Param("title") String title);
    /*
     * 通过任务id修改任务截至时间
     * */
    @Update("update task set end_time = #{newTime} where id=#{id}")
    public boolean alterTaskEndTimeById(@Param("newTime") Date newTime,
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
    @Update("update task set area_id = #{areaId} where id=#{id}")
    public boolean alterTaskAreaById(@Param("areaId") Integer areaId,
                                        @Param("id") Integer id);

    /*
     * 通过任务标题修改任务分区
     * */
    @Update("update task set area_id = #{areaId} where title=#{title}")
    public boolean alterTaskAreaByTitle(@Param("areaId") Integer areaId,
                                           @Param("title") String title);

    /*
    * 通过标题更新整条任务
    * */
    @Update("update task set id = #{id},title = #{title},user_id = #{userId},content = #{content},area_id = #{areaId},super_id = #{superId},level = #{level},begin_time = #{beginTime},end_time = #{endTime},state = #{state},finish_rate = #{finishRate} where title=#{oldTitle}")
    public boolean alterTaskByTitle(Task task, @Param("oldTitle") String title);

    /*
     * 通过id更新整条任务
     * */
    @Update("update task set title = #{title},level = #{level},begin_time = #{beginTime},end_time = #{endTime},finish_rate = #{finishRate} where id=#{id}")
    public boolean alterTask(Task task);

    /*
     * 通过父id更新子任务优先级
     * */
    @Update("update task set level = #{level} where super_id=#{superId}")
    public boolean alterTaskLevel(@Param("level") Integer level, @Param("superId") Integer superId);

    /*
     * 通过任务id修改任务状态
     * */
    @Update("update task set state = #{newState} where id=#{id}")
    public boolean alterTaskStateById(@Param("newState") Integer newState,
                                     @Param("id") Integer id);

    /*
     * 通过任务标题修改任务状态
     * */
    @Update("update task set state = #{newState} where title=#{title}")
    public boolean alterTaskStateByTitle(@Param("newState") Integer newState,
                                        @Param("title") String title);

    /*
     * 通过任务id修改任务完成率
     * */
    @Update("update task set finish_rate = #{newRate} where id=#{id}")
    public boolean alterTaskRateById(@Param("newRate") Double finishRate,
                                      @Param("id") Integer id);

    /*
     * 通过任务标题修改任务完成率
     * */
    @Update("update task set finish_rate = #{newRate} where title=#{title}")
    public boolean alterTaskRateByTitle(@Param("newRate") Double finish_rate,
                                         @Param("title") String title);

    /*
     * 通过子任务id修改子任务父任务id
     * */
    @Update("update task set super_id = #{newId} where id=#{id}")
    public boolean alterTaskSuperIdById(@Param("newId") Integer superId,
                                     @Param("id") Integer id);

    /*
     * 通过子任务标题修改子任务的父任务id
     * */
    @Update("update task set super_id = #{newId} where title=#{title}")
    public boolean alterTaskSuperIdByTitle(@Param("newId") Integer superId,
                                        @Param("title") String title);

    /*
     * 通过任务id修改任务备注内容
     * */
    @Update("update task set content = #{content} where id=#{id}")
    public boolean alterTaskContentById(@Param("content") String content,
                                        @Param("id") Integer id);

    /*
     * 获取一个用户的所有任务对象
     * */
    @Select("select * from task where user_id = #{userId}")
    public List<Task> getAllTasksByUserId(@Param("userId") Integer userId);

    /*
     * 获取用户近一周的任务总数
     * */
    @Select("select ifnull(b.count,0) as count\n" +
            "from (\n" +
            "    SELECT curdate() as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 1 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 2 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 3 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 4 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 5 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 6 day) as click_date\n" +
            ") a left join (\n" +
            "  select DATE_FORMAT(begin_time, '%Y-%m-%d' ) as datetime, count(*) as count\n" +
            "  from task\n" +
            "\twhere super_id = 0 and user_id=#{userId} and level > 1\n" +
            "  group by begin_time\n" +
            ") b on a.click_date = b.datetime;")
    public List<Integer> getTotalTaskNumNearlySeven(@Param("userId") Integer userId);

    /*
     * 获取用户近一周已完成的任务数
     * */
    @Select("select ifnull(b.count,0) as count\n" +
            "from (\n" +
            "    SELECT curdate() as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 1 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 2 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 3 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 4 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 5 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 6 day) as click_date\n" +
            ") a left join (\n" +
            "  select DATE_FORMAT(begin_time, '%Y-%m-%d' ) as datetime, count(*) as count\n" +
            "  from task\n" +
            "\twhere super_id = 0 and user_id=#{userId} and level > 1 and finish_rate = 100\n" +
            "  group by begin_time\n" +
            ") b on a.click_date = b.datetime;")
    public List<Integer> getCplTaskNumNearlySeven(@Param("userId") Integer userId);


    /*
     * 获取用户本月的任务总数
     * */
    @Select("SELECT ifnull(b.num,0) count FROM(\n" +
            " \n" +
            "select date from (\n" +
            "SELECT DATE_FORMAT(DATE_SUB(last_day(curdate()), INTERVAL xc-1 day), '%Y-%m-%d') as date\n" +
            "    FROM (\n" +
            "        SELECT @xi:=@xi+1 as xc from\n" +
            "            (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) xc1,\n" +
            "            (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) xc2,\n" +
            "            (SELECT @xi:=0) xc0\n" +
            "        ) xcxc) x0 where x0.date >= (select date_add(curdate(),interval-day(curdate())+1 day)) ORDER BY date asc) a \n" +
            " \n" +
            "LEFT JOIN\n" +
            "    (SELECT\n" +
            "        count(*) num,DATE_FORMAT( begin_time, '%Y-%m-%d' ) dates\n" +
            "        from task\n" +
            "        where super_id = 0 and user_id=#{userId} and level > 1" +
            "        GROUP BY dates) b \n" +
            " \n" +
            "ON a.date=b.dates ORDER BY date asc")
    public List<Integer> getTotalTaskNumThisMonth(@Param("userId") Integer userId);

    /*
     * 获取用户本月已完成的任务数
     * */
    @Select("SELECT ifnull(b.num,0) count FROM(\n" +
            " \n" +
            "select date from (\n" +
            "SELECT DATE_FORMAT(DATE_SUB(last_day(curdate()), INTERVAL xc-1 day), '%Y-%m-%d') as date\n" +
            "    FROM (\n" +
            "        SELECT @xi:=@xi+1 as xc from\n" +
            "            (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) xc1,\n" +
            "            (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) xc2,\n" +
            "            (SELECT @xi:=0) xc0\n" +
            "        ) xcxc) x0 where x0.date >= (select date_add(curdate(),interval-day(curdate())+1 day)) ORDER BY date asc) a \n" +
            " \n" +
            "LEFT JOIN\n" +
            "    (SELECT\n" +
            "        count(*) num,DATE_FORMAT( begin_time, '%Y-%m-%d' ) dates\n" +
            "        from task\n" +
            "        where super_id = 0 and user_id=#{userId} and level > 1 and finish_rate = 100" +
            "        GROUP BY dates) b \n" +
            " \n" +
            "ON a.date=b.dates ORDER BY date asc")
    public List<Integer> getCplTaskNumThisMonth(@Param("userId") Integer userId);

    /*
     * 获取用户本年12个月的任务总数
     * */
    @Select("SELECT IFNULL(b.con,0) as c FROM(SELECT  STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d') as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 1 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 2 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 3 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 4 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 5 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 6 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 7 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 8 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 9 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 10 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 11 MONTH) as click_date) a LEFT JOIN(\n" +
            "SELECT COUNT(*) as con , CONCAT(YEAR(begin_time),'-',MONTH(begin_time)) as mon FROM task\n" +
            "WHERE user_id=#{userId} and level > 1 and super_id = 0\n" +
            "GROUP BY mon)\n" +
            "b ON CONCAT(YEAR(click_date),'-',MONTH(click_date))=b.mon")
    public List<Integer> getTotalTaskNumThisYear(@Param("userId") Integer userId);

    @Select("SELECT IFNULL(b.con,0) as c FROM(SELECT  STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d') as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 1 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 2 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 3 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 4 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 5 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 6 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 7 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 8 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 9 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 10 MONTH) as click_date UNION ALL\n" +
            "SELECT  DATE_ADD(STR_TO_DATE(CONCAT(YEAR(CURDATE()),'-',1,'-',1) , '%Y-%m-%d'),INTERVAL 11 MONTH) as click_date) a LEFT JOIN(\n" +
            "SELECT COUNT(*) as con , CONCAT(YEAR(begin_time),'-',MONTH(begin_time)) as mon FROM task\n" +
            "WHERE user_id=#{userId} and level > 1 and super_id = 0 and finish_rate = 100\n" +
            "GROUP BY mon)\n" +
            "b ON CONCAT(YEAR(click_date),'-',MONTH(click_date))=b.mon")
    public List<Integer> getCplTaskNumThisYear(@Param("userId") Integer userId);
}
