package com.learnspring.springblogger.services.serviImp;

import com.learnspring.springblogger.dtos.UserDto;
import com.learnspring.springblogger.exceptions.UserNotFoundException;
import com.learnspring.springblogger.models.User;
import com.learnspring.springblogger.repositories.UserRepository;
import com.learnspring.springblogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());

        User newUser = userRepository.save(user);

        UserDto response = new UserDto();
        response.setUserName(newUser.getUserName());
        response.setUserEmail(newUser.getUserEmail());
        response.setUserPassword(newUser.getUserPassword());

        return response;
    }

    @Override
    public List<UserDto> getAllUsers(int pageNo, int pageSize) {

        Pageable pageable =  PageRequest.of(pageNo,pageSize);

        Page<User> users = userRepository.findAll(pageable);

        List<User> userList = users.getContent();

        if (userList.size() <= 0){
            return null;
        }

        return  userList.stream().map(user -> modelToDto(Optional.ofNullable(user))).collect(Collectors.toList());
    }

    @Override
    public UserDto getSingleUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent() == false){
            throw new UserNotFoundException("This User doesn't exists");
        }

        return modelToDto(user);

    }

    @Override
    public UserDto updateUserById(Long userId,UserDto userDto) {

       Optional<User> user = userRepository.findById(userId);

       user.get().setUserId(userId);
       user.get().setUserName(userDto.getUserName());
       user.get().setUserEmail(userDto.getUserEmail());
       user.get().setUserPassword(userDto.getUserPassword());

       User updatedUser = userRepository.save(user.get());

       return modelToDto(Optional.of(updatedUser));
    }

    @Override
    public void deleteUser(Long userId) {

        UserDto userDto = getSingleUserById(userId);

        User user = dtoToModel(userDto);

        userRepository.delete(user);

    }

    // Mapper to convert User Objects to UserDtos
    public UserDto modelToDto(Optional<User> user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.get().getUserId());
        userDto.setUserName(user.get().getUserName());
        userDto.setUserEmail(user.get().getUserEmail());
        userDto.setUserPassword(user.get().getUserPassword());
        return userDto;
    }

    public User dtoToModel(UserDto userDto){
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());
        return user;
    }
}
