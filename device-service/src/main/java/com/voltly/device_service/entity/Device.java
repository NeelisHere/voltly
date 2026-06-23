package com.voltly.device_service.entity;

import com.voltly.common_lib.dto.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "voltly_registered_devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    DeviceType type;
    String location;
    Long userId;
}
