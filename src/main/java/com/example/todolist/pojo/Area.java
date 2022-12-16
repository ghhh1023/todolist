package com.example.todolist.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Area {
    private Integer area_id;
    private String area_name;
    private Integer picture_id;

    @Override
    public String toString() {
        return "Area{" +
                "area_id=" + area_id +
                ", area_name='" + area_name + '\'' +
                ", picture_id=" + picture_id +
                '}';
    }
}
