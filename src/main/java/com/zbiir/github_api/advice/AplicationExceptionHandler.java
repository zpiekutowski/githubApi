package com.zbiir.github_api.advice;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class AplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,String> handlerUserNotExistException(UserNotExistException ex){
        Map <String,String> errorMap = new LinkedHashMap<>();
        errorMap.put("status","404");
        errorMap.put("message",ex.getMessage());
        return errorMap;
    }

}
