package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Text {
    private Integer textId;
    private String textVal;
    private Integer contentId;
}
