package com.example.todolist.mapper;



import com.example.todolist.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {

    @Select("select * from user_info where id=#{id}")
    public UserInfo getUserInfoById(@Param("id") Integer id);

    @Update({"update user_info set picture_src=#{pictureSrc},name=#{name},sex=#{sex},birth=#{birth},sign=#{sign} where id=#{id}"})
    public boolean alterUserInfo(UserInfo userInfo);




}
