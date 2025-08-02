package com.zbiir.github_api.advice;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message){
            super(message);
    }

}
