package com.boring.cloud.consumermovie.controller;

import com.boring.cloud.consumermovie.client.UserFeignClient;
import com.boring.cloud.consumermovie.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author boring
 * @date 2020/8/3 上午10:30
 */
@RequestMapping("/movies")
@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;
    @Qualifier("microservice-provider-user")
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) {
        User user = this.userFeignClient.findById(id);
        // ...电影微服务的业务...
        return user;
    }
}
