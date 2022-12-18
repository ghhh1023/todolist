package com.example.todolist.mapper;

import com.example.todolist.pojo.Content;
import com.example.todolist.pojo.Path;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PathMapper {
    /*
    * 根据path_id 获取路径
    * */
    @Select("select * from path where path_id = #{id}")
    public Path getPathById(@Param("id") Integer id);

    /*
    * 新增path
    * */
    @Insert({"insert into path(path_id,path_val) values(#{id},#{val})"})
    public boolean addPath(Path path);

    /*
    * 通过id 删除path
    * */
    @Delete("delete from path where path_id=#{id}")
    public boolean deletePath(@Param("id") Integer pathId);

    /*
    * 通过id更新path
    * */
    @Update("update path set path_val=#{val} where path_id=#{id}")
    public boolean updatePath(@Param("val") String val,@Param("id") Integer pathId);
}
