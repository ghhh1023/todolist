package com.example.todolist.mapper;

import com.example.todolist.pojo.Area;
import com.example.todolist.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AreaMapper {

    /*
    * 根据分区名返回分区
    * */
    @Select("select * from area where area_name=#{area_name}")
    public Area getAreaByAreaName(@Param("area_name") String area_name);

    /*
    * 根据分区id返回分区
    * */
    @Select("select * from area where area_id=#{area_id}")
    public Area getAreaByAreaId(@Param("area_id") Integer area_id);

    /*
    * 获取所有分区对象
    * */
    @Select("select * from area")
    public List<Area> getAllAreas();

    /*
    * 通过分区ID更新分区图片
    * */
    @Update("update area set picture_id=#{picture_id} where area_id=#{area_id}")
    public boolean alterPictureById(@Param("picture_id") Integer picture_id,
                                    @Param("id") Integer area_id);

    /*
    * 通过分区名更新分区图片
    * */
    @Update("update area set picture_id=#{picture_id} where area_name=#{area_name}")
    public boolean alterPictureByName(@Param("picture_id") Integer picture_id,
                                      @Param("area_name") String area_name);

    /*
    * 通过分区id修改分区名字
    * */
    @Update("update area set area_name=#{area_name} where area_id=#{area_id}")
    public boolean alterPictureByName(@Param("area_name") String area_name,
                                      @Param("area_id") Integer area_id);

    /*
    * 通过分区名字修改分区名字
    * */
    @Update("update area set area_name=#{new_name} where area_name=#{area_name}")
    public boolean alterPictureByName(@Param("new_name") String new_name,
                                      @Param("area_name") String area_name);

    /*
    * 新增分区
    * */
    @Insert({"insert into area(area_id,area_name,picture_id) values(#{area_id},#{area_name},#{picture_id})"})
    public boolean insertArea(Area area);

    /*
    * 通过id删除分区
    * */
    @Delete({"delete from area where area_id=#{id}"})
    public boolean deleteAreaById(@Param("id") Integer area_id);

    /*
     * 通过分区名删除分区
     * */
    @Delete({"delete from area where area_name=#{name}"})
    public boolean deleteAreaById(@Param("name") String area_name);

    /*
    * 返回分区数量
    * */
    @Select("select count(*) from area")
    public Integer getAreaCount();
}
