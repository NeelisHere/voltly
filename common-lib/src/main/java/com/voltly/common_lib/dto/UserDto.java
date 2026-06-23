package com.voltly.common_lib.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String sub;
    String name;
    String email;
    String address;
    boolean alerting = true;
    Double energyAlertingThreshold;
}
