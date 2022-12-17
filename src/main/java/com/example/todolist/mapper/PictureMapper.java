package com.example.todolist.mapper;

import com.example.todolist.pojo.Content;
import com.example.todolist.pojo.Path;
import com.example.todolist.pojo.Picture;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PictureMapper {
    /*
     * 根据picture_id 获取路径
     * */
    @Select("select path_id from picture where picture_id = #{id}")
    public Integer getPathById(@Param("id") Integer picture_id);

    /*
     * 新增picture
     * */
    @Insert({"insert into picture(picture_id,path_id) values(#{picture_id},#{path_id})"})
    public boolean addPicture(Picture picture);

    /*
     * 通过id 删除path
     * */
    @Delete("delete from picture where picture_id=#{id}")
    public boolean deletePicture(@Param("id") Integer picture_id);

    /*
     * 通过id更新path
     * */
    @Update("update picture set path_id=#{path_id} where path_id=#{id}")
    public boolean updatePicture(@Param("path_id") Integer path_id,@Param("id") Integer picture_id);
}
