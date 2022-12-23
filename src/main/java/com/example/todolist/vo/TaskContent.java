package com.example.todolist.vo;

import com.example.todolist.pojo.TaskPicture;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskContent {
    private Integer taskId;
    private String content;
    private List<TaskPicture> taskPictureList;
}
