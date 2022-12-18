package com.example.todolist.mapper;

import com.example.todolist.pojo.Content;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ContentMapper {
    /*
    * 根据content_id查询指定内容
    * */
    @Select("select * from content where content_id = #{id}")
    public Content getContentById(@Param("id") Integer id);

    /*
    * 根据content_id删除指定内容
    * */
    @Delete("delete from content where content_id = #{id}")
    public boolean deleteContentById(@Param("id") Integer id);

    /*
     * 根据content_id更新text_id
     * */
    @Update("update content set text_id=#{textId} where content_id = #{id}")
    public boolean alterTextById(@Param("textId") Integer text_id,@Param("id") Integer contentId);

    /*
     * 根据content_id更新picture_id
     * */
    @Update("update content set picture_id=#{pictureId} where content_id = #{id}")
    public boolean alterPictureById(@Param("pictureId") Integer pictureId,@Param("id") Integer contentId);

    /*
     * 根据content_id更新path_id
     * */
    @Update("update content set path_id=#{pathId} where content_id = #{id}")
    public boolean alterPathById(@Param("pathId") Integer pathId,@Param("id") Integer contentId);

    /*
    * 增加一条content
    * */
    @Insert("insert into content(content_id,text_id,picture_id,path_id) values(#{contentId},#{textId},#{pictureId},#{pathId})")
    public boolean insertContent(Content content);

    /*
    * 通过content_id删除一条记录
    * */
    @Delete("delete from content where content_id=#{id}")
    public boolean deleteContent(@Param("id") Integer contentId);
}
