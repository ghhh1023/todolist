package com.example.todolist.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAreaList {
   private Integer id;
   private String title;
   private Integer emgCplNum;
   private Integer emgTotalNum;
   private Integer cmnCplNum;
   private Integer cmnTotalNum;
}
