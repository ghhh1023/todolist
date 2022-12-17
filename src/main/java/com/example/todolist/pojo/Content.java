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
    private Integer content_id;
    private Integer text_id;
    private Integer picture_id;
    private Integer path_id;
}
