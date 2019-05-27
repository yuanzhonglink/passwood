package com.pass.util.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回信息实体类
 * @author yuanzhonglin
 * @date 2019/5/27
 */
@Data
public class Message<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回的数据类
     */
    private T data;

    /**
     * 时间
     */
    private Long time;

    public Message(Integer code, String message, T data, Long time) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = time;
    }
}
