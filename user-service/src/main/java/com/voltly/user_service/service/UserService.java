package com.voltly.user_service.service;

import com.voltly.common_lib.dto.UserDto;
import com.voltly.common_lib.exception.UserNotFoundException;
import com.voltly.user_service.entity.User;
import com.voltly.user_service.mapper.UserMapper;
import com.voltly.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId, String.valueOf(HttpStatus.NOT_FOUND.value())));
        return UserMapper.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User user = userRepository.findUserBySub(userDto.getSub())
                .orElse(null);
        if (user == null) {
            user = userRepository.save(UserMapper.toEntity(userDto));
            return UserMapper.toDto(user);
        } else {
            return null;
        }
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userRepository
                .findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId(), String.valueOf(HttpStatus.NOT_FOUND.value())));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setAlerting(userDto.isAlerting());
        user.setEnergyAlertingThreshold(userDto.getEnergyAlertingThreshold());

        return UserMapper.toDto(userRepository.save(user));
    }

    public void deleteUserById(Long userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new UserNotFoundException(userId, String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        userRepository.deleteById(userId);
    }
}
