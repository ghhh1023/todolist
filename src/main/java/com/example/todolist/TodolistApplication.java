package com.example.todolist;

import com.example.todolist.utils.SpringUtil;
//import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodolistApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(TodolistApplication.class, args);
        SpringUtil.setApplicationContext(app);
    }

}
