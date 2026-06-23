package com.voltly.usage_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "energy_usage")
@IdClass(EnergyUsage.EnergyUsageId.class)
public class EnergyUsage {
    @Id
    Long deviceId;
    
    @Id
    LocalDateTime timestamp;
    
    Double energyConsumed;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnergyUsageId implements Serializable {
        private Long deviceId;
        private LocalDateTime timestamp;
    }
}
