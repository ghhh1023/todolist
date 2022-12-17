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
    @Select("select *from text where content_id=#{content_id}")
    public List<Text> getAllTextById(@Param("content_id")Integer content_id);

    /*
     * 根据text_id 修改文本内容
     * */
    @Update("update text set text_val=#{text_val} where text_id=#{id}")
    public boolean alterTextById(@Param("text_val") String val,@Param("id") Integer text_id);

    /*
    * 通过text_id删除文本
    * */
    @Delete("delete from text where text_id=#{id}")
    public boolean deleteTextById(@Param("id") Integer text_id);

    /*
    * 新增一条text文本
    * */
    @Insert("insert into text(text_id,text_val,content_id) values (#{text_id},#{text_val},#{content_id})")
    public boolean insertText(Text text);
}
