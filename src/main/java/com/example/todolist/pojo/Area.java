package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private Integer area_id;
    private String area_name;
    private Integer picture_id;
    private Integer user_id;

    @Override
    public String toString() {
        return "Area{" +
                "area_id=" + area_id +
                ", area_name='" + area_name + '\'' +
                ", picture_id=" + picture_id +
                ", user_id=" + user_id +
                '}';
    }
}
