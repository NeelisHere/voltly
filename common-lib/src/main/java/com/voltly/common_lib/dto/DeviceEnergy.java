package com.voltly.common_lib.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceEnergy {
    Long deviceId;
    Double energyConsumed;
    Long userId;
}
