package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
//    code == 0 失败 code == 1成功
    private Integer code;
//    msg 后端提示信息
    private String msg;
//    data 后端传输数据
    private Object data;

    public static Result error()
    {
        return new Result(0, "error", null);
    }

    public static Result error(String msg)
    {
        return new Result(0, msg, null);
    }

    public static Result success()
    {
        return new Result(1, "success", null);
    }
    public static Result success(Object data)
    {
        return new Result(1, "success", data);
    }

    public static Result success(String msg, Object data)
    {
        return new Result(1, msg, data);
    }
}
