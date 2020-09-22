package com.boring.cloud.consumermovie.controller;

import com.boring.cloud.consumermovie.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author boring
 * @date 2020/8/3 上午10:30
 */
@RequestMapping("/movies")
@RestController
public class MovieController {

    private Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFallback") // 指定降级方法
    public User findById(@PathVariable Long id) {
        // 这里用到了RestTemplate的占位符能力
        User user = this.restTemplate.getForObject("http://microservice-provider-user/user/{id}", User.class, id);
        // ...电影微服务的业务...
        return user;
    }

    /**
     * 服务降级调用的回退方法
     * @param id id
     * @param throwable 可选参数，携带服务降级的错误原因
     * @return 临时返回数据
     */
    public User findByIdFallback(Long id, Throwable throwable) {
        log.error("进入回退方法,错误原因:" + throwable.getMessage());
        return new User(id, "游客", "游客", 18, BigDecimal.valueOf(100));
    }
}
