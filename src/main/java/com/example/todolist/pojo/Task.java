package com.example.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private Integer userId;
    private Integer contentId;
    private Integer areaId;
    private Integer superId;
    private Integer level;
    private Date beginTime;
    private Date endTime;
    private Integer state;
    private Integer finishRate;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", areaId=" + areaId +
                ", superId=" + superId +
                ", level=" + level +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", finishRate=" + finishRate +
                '}';
    }
}
