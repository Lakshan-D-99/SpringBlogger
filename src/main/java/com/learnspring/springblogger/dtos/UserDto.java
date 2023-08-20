package com.learnspring.springblogger.dtos;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
