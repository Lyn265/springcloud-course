package com.lyn.server.exception;

import com.lyn.server.enums.BusinessExceptionCode;

public class BusinessException extends RuntimeException {
    public BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code){
        super(code.getDesc());
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
