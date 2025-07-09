package org.aiboot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("org.aiboot.mapper")
@ComponentScan(basePackages = {"org.aiboot.*"})
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("------------------------------");
        log.info("-   the service is running   -");
        log.info("------------------------------");
    }

}
