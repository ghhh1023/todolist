package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPicture {
    private Integer id;
    private Integer taskId;
    private String pictureSrc;
}
