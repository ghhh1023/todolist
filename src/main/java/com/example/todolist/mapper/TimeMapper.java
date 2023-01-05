package com.example.todolist.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TimeMapper {
    @Select("select a.click_date\n" +
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
            ") b on a.click_date = b.datetime;")
    public List<Date> getTimeList();
}
