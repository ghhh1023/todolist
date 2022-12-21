package com.example.todolist.utils;

import com.example.todolist.pojo.Area;

import java.lang.reflect.Field;

public class CopyFieldValue {
    public static void copyFieldValue(Object newObject, Object pastObject){
        for(Field f : newObject.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                if(f.get(newObject) == null&&f.get(pastObject) != null){
                    f.set(newObject,f.get(pastObject));
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
