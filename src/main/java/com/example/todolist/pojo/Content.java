package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private Integer contentId;
    private Integer textId;
    private Integer pictureId;
    private Integer pathId;
}
