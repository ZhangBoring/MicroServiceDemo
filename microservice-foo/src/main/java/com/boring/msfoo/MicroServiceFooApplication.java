package com.boring.msfoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author boring
 * @date 2020/8/23 下午7:38
 */
@SpringBootApplication
@RestController
public class MicroServiceFooApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceFooApplication.class);
    }
    @Value("${profile}")
    private String profile;

    @GetMapping("/profile")
    public String profile() {
        return profile;
    }
}
