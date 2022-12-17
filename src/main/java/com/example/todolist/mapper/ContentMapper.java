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
    @Update("update content set text_id=#{text_id} where content_id = #{id}")
    public boolean alterTextById(@Param("text_id") Integer text_id,@Param("id") Integer content_id);

    /*
     * 根据content_id更新picture_id
     * */
    @Update("update content set picture_id=#{picture_id} where content_id = #{id}")
    public boolean alterPictureById(@Param("picture_id") Integer picture_id,@Param("id") Integer content_id);

    /*
     * 根据content_id更新path_id
     * */
    @Update("update content set path_id=#{path_id} where content_id = #{id}")
    public boolean alterPathById(@Param("path_id") Integer path_id,@Param("id") Integer content_id);

    /*
    * 增加一条content
    * */
    @Insert("insert into content(content_id,text_id,picture_id,path_id) values(#{content_id},#{text_id},#{picture_id},#{path_id})")
    public boolean insertContent(Content content);

    /*
    * 通过content_id删除一条记录
    * */
    @Delete("delete from content where content_id=#{id}")
    public boolean deleteContent(@Param("id") Integer content_id);
}
