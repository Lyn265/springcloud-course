package com.lyn.system.controller;

import com.lyn.server.dto.ResponseDto;
import com.lyn.server.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseDto businessExceptionHandler(BusinessException ex){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMsg(ex.getCode().getDesc());
        return responseDto;
    }
}
