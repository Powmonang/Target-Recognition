package com.lzk.handle;

import com.lzk.entity.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
捕获异常类
用于处理数据库里插入时 主键重复的报错 返回给前端ID重复
* */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public Result ex(Exception ex){

        return Result.error("ID重复");
    }
}
