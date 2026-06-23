package com.voltly.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "VoltlyRegisteredUsers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String sub;
    String name;
    String email;
    String address;
    boolean alerting;
    Double energyAlertingThreshold;
}
