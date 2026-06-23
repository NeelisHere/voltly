package com.voltly.usage_service.repository;

import com.voltly.common_lib.dto.DeviceEnergy;
import com.voltly.usage_service.entity.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsageRepository extends JpaRepository<EnergyUsage, EnergyUsage.EnergyUsageId> {
    @Query("""
        SELECT new com.voltly.common_lib.dto.DeviceEnergy(
            e.deviceId,
            SUM(e.energyConsumed),
            null
        )
        FROM EnergyUsage e
        WHERE e.timestamp >= :oneHourAgo
        GROUP BY e.deviceId
    """)
    List<DeviceEnergy> getTotalEnergyUsageByDeviceInLastXHours(LocalDateTime oneHourAgo);
}
