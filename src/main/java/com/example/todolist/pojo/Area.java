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
    private Integer areaId;
    private String areaName;
    private Integer pictureId;
    private Integer userId;

    @Override
    public String toString() {
        return "Area{" +
                "area_id=" + areaId +
                ", area_name='" + areaName + '\'' +
                ", picture_id=" + pictureId +
                ", user_id=" + userId +
                '}';
    }
}
