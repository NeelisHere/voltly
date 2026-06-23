package com.voltly.usage_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Slf4j
public class TimescaleDbConfig {

    @Bean
    CommandLineRunner initTimescaleDb(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                log.info("Enabling TimescaleDB extension...");

                jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS timescaledb;");

                log.info("TimescaleDB extension enabled successfully.");
                log.info("Converting energy_usage table to hypertable...");

                String createHypertableQuery = """
                    SELECT create_hypertable('energy_usage', 'timestamp',
                        if_not_exists => TRUE,
                        migrate_data => TRUE
                    );
                    """;
                jdbcTemplate.execute(createHypertableQuery);

                log.info("Table energy_usage successfully converted to TimescaleDB hypertable.");
                
            } catch (Exception e) {
                log.error("Error initializing TimescaleDB: {}", e.getMessage(), e);
                throw e;
            }
        };
    }
}

