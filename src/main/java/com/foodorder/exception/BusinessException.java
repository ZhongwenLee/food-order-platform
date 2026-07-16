package com.foodorder.exception;

import lombok.Getter;
import com.foodorder.common.constant.ResultCode;

@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;
    public BusinessException(String message) {
        this(ResultCode.BAD_REQUEST.getCode(), message);
    }
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
    }
    public BusinessException(ResultCode resultCode, String message) {
        this(resultCode.getCode(), message);
    }
}
