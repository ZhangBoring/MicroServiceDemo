package com.boring.cloud;

import com.boring.cloud.dao.UserDAO;
import com.boring.cloud.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * @author boring
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ProviderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }

    @Bean
    ApplicationRunner init(UserDAO userDAO) {
        return obj -> {
            User user1 = new User(1L, "account1", "张三", 20, new BigDecimal("100"));
            User user2 = new User(2L, "account2", "李四", 28, new BigDecimal("100"));
            User user3 = new User(3L, "account3", "王五", 30, new BigDecimal("200"));
            Stream.of(user1, user2, user3).forEach(userDAO::save);
        };
    }
}
