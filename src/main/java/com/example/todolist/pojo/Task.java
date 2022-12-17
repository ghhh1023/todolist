package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private Integer user_id;
    private Integer content_id;
    private Integer area_id;
    private Integer super_id;
    private Integer level;
    private Date beginTime;
    private Date endTime;
    private Integer state;
    private Double finish_rate;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user_id=" + user_id +
                ", content_id=" + content_id +
                ", area_id=" + area_id +
                ", super_id=" + super_id +
                ", level=" + level +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", finish_rate=" + finish_rate +
                '}';
    }
}
