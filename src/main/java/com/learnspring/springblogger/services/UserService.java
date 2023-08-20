package com.learnspring.springblogger.services;

import com.learnspring.springblogger.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createNewUser(UserDto userDto);

    List<UserDto> getAllUsers(int pageNo, int pageSize);

    UserDto getSingleUserById(Long userId);

    UserDto updateUserById(Long userId, UserDto userDto);

    void deleteUser(Long userId);
}
