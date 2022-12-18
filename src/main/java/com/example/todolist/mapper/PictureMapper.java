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
    public Integer getPathById(@Param("id") Integer pictureId);

    /*
     * 新增picture
     * */
    @Insert({"insert into picture(picture_id,path_id) values(#{pictureId},#{pathId})"})
    public boolean addPicture(Picture picture);

    /*
     * 通过id 删除path
     * */
    @Delete("delete from picture where picture_id=#{id}")
    public boolean deletePicture(@Param("id") Integer pictureId);

    /*
     * 通过id更新path
     * */
    @Update("update picture set path_id=#{pathId} where path_id=#{id}")
    public boolean updatePicture(@Param("pathId") Integer pathId,@Param("id") Integer pictureId);
}
