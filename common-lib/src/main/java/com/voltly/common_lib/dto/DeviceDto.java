package com.voltly.common_lib.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceDto {
    Long id;
    String name;
    DeviceType type;
    String location;
    Long userId;
}
