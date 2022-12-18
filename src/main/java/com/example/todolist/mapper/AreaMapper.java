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
    @Select("select * from area where areaName like #{areaName}")
    public Area getAreaByAreaName(@Param("areaName") String areaName);

    /*
    * 根据分区id返回分区
    * */
    @Select("select * from area where area_id = #{areaId}")
    public Area getAreaByAreaId(@Param("areaId") Integer areaId);

    /*
    * 获取所有分区对象
    * */
    @Select("select * from area")
    public List<Area> getAllAreas();

    /*
    * 通过分区ID更新分区图片
    * */
    @Update("update area set picture_id=#{pictureId} where area_id=#{areaId}")
    public boolean alterPictureById(@Param("pictureId") Integer pictureId,
                                    @Param("areaId") Integer areaId);

    /*
    * 通过分区名更新分区图片
    * */
    @Update("update area set picture_id=#{pictureId} where area_name like #{areaName}")
    public boolean alterPictureByName(@Param("pictureId") Integer pictureId,
                                      @Param("areaName") String areaName);

    /*
    * 通过分区id修改分区名字
    * */
    @Update("update area set area_name=#{areaName} where area_id=#{areaId}")
    public boolean alterNameById(@Param("areaName") String areaName,
                                      @Param("areaId") Integer areaId);

    /*
    * 通过分区名字修改分区名字
    * */
    @Update("update area set area_name=#{newName} where area_name like #{areaName}")
    public boolean alterNameByName(@Param("newName") String newName,
                                      @Param("areaName") String areaName);

    /*
    * 新增分区
    * */
    @Insert({"insert into area(area_name,picture_id,user_id) values(#{areaName},#{pictureId},#{userId})"})
    public boolean insertArea(Area area);

    /*
    * 通过id删除分区
    * */
    @Delete({"delete from area where area_id=#{areaId}"})
    public boolean deleteAreaById(@Param("areaId") Integer areaId);

    /*
     * 通过分区名删除分区
     * */
    @Delete({"delete from area where area_name like #{areaName}"})
    public boolean deleteAreaByName(@Param("areaName") String areaName);

    /*
    * 返回分区数量
    * */
    @Select("select count(*) from area")
    public Integer getAreaCount();

    /*
     * 通过用户id查询分区
     * */
    @Select("select * from area where user_id=#{userId}")
    public List<Area> getAreaByUserId(@Param("userId") Integer userId);
}
