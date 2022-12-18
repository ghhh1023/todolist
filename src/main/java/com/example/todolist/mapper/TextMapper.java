package com.example.todolist.mapper;

import com.example.todolist.pojo.Text;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TextMapper {
    /*
    * 根据text_id 查询文本内容
    * */
    @Select("select *from text where text_id=#{id}")
    public Text getTextById(@Param("id")Integer id);


    /*
    * 根据content_id 查询所有文本内容
    * */
    @Select("select *from text where content_id=#{contentId}")
    public List<Text> getAllTextById(@Param("contentId")Integer contentId);

    /*
     * 根据text_id 修改文本内容
     * */
    @Update("update text set text_val=#{textVal} where text_id=#{id}")
    public boolean alterTextById(@Param("textVal") String val,@Param("id") Integer textId);

    /*
    * 通过text_id删除文本
    * */
    @Delete("delete from text where text_id=#{id}")
    public boolean deleteTextById(@Param("id") Integer textId);

    /*
    * 新增一条text文本
    * */
    @Insert("insert into text(text_id,text_val,content_id) values (#{textId},#{textVal},#{contentId})")
    public boolean insertText(Text text);
}
