package com.voltly.user_service.mapper;

import com.voltly.common_lib.dto.UserDto;
import com.voltly.user_service.entity.User;

public final class UserMapper {

    private UserMapper() {
        // Prevent instantiation
    }

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .sub(user.getSub())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                // id intentionally ignored
                .sub(userDto.getSub())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .alerting(userDto.isAlerting())
                .energyAlertingThreshold(userDto.getEnergyAlertingThreshold())
                .build();
    }
}