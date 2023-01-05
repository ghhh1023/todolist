package com.example.todolist.service.serviceImpl;

import com.example.todolist.mapper.TimeMapper;
import com.example.todolist.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {
    @Autowired
    TimeMapper timeMapper;

    @Override
    public List<Date> getTimeList() {
        return timeMapper.getTimeList();
    }
}
