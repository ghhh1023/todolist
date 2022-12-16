package com.example.todolist.pojo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private  Integer id;


    @Length(max = 11,min = 11,message = "手机号长度必须是11位")
    private String phone;

    
    private Integer pictureId;

    private String name;

    private Integer sex;

    private Date birth;

    private String sign;
}
