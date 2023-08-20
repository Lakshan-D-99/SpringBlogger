package com.learnspring.springblogger.exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1;

    public UserNotFoundException(String errMsg){
        super(errMsg);
    }
}
