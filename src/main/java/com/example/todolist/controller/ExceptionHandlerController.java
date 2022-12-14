package com.example.todolist.controller;

import com.example.todolist.common.RetJson;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * 处理Hibernate Validator校验Bean参数抛出异常
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RetJson handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 1.校验
        Boolean fieldErrorUnobtainable = (e == null || e.getBindingResult() == null
                || CollectionUtils.isEmpty(e.getBindingResult().getAllErrors()) || e.getBindingResult().getAllErrors().get(0) == null);
        if (fieldErrorUnobtainable) {
            return RetJson.success(0, "ok");
        }
        // 2.return
        return RetJson.fail(-1, "参数不正确");
    }

    /**
     * 处理Hibernate Validator校验URL参数抛出异常
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public RetJson handlerConstraintViolationException(ConstraintViolationException e) {
        // 1.校验
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return RetJson.success(0, "ok");
        }
        // 2.return
        return RetJson.fail(-1, "参数有误");
    }
}
