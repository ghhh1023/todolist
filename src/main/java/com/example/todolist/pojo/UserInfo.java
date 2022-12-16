package com.example.todolist.pojo;

import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private  Integer id;


    @Length(max = 11,min = 11,message = "手机号长度必须是11位")
    private String phoneNum;

    
    private Integer pictureId;
}
