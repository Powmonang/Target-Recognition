package com.defectscan.handler;

import com.defectscan.constant.MessageConstant;
import com.defectscan.entity.Result;
import com.defectscan.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/*
捕获异常类
用于处理数据库里插入时 主键重复的报错 返回给前端ID重复
* */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public Result ex(Exception ex){

        return Result.error("用户名重复");
    }

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public com.defectscan.result.Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return com.defectscan.result.Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public com.defectscan.result.Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return com.defectscan.result.Result.error(msg);
        }else{
            return com.defectscan.result.Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
