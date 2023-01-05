package com.example.todolist.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataList {
    private List<Integer> weekTotalList;
    private List<Integer> weekCplList;
    private List<Double> weekRateList;
    private List<Integer> monthTotalList;
    private List<Integer> monthCplList;
    private List<Double> monthRateList;
    private List<Integer> YearTotalList;
    private List<Integer> YearCplList;
    private List<Double> YearRateList;
}
