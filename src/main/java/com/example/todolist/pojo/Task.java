package com.example.todolist.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private Integer userId;
    private String content;
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
                ", content=" + content +
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
