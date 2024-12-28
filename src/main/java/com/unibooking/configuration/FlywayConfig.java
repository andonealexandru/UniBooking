package com.unibooking.configuration;

import lombok.AllArgsConstructor;
//import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class FlywayConfig {

    private final Environment environment;

    public void test() {
        System.out.println("test");
    }

//    @Bean(initMethod = "migrate")
//    public Flyway flyway() {
//        return new Flyway(Flyway.configure()
//                .baselineOnMigrate(true)
//                .dataSource(environment.getRequiredProperty("spring.datasource.url"),
//                        environment.getRequiredProperty("spring.datasource.username"),
//                        environment.getRequiredProperty("spring.datasource.password"))
//        );
//    }

}
