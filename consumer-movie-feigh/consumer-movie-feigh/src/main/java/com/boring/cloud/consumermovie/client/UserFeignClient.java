package com.boring.cloud.consumermovie.client;

import com.boring.cloud.consumermovie.entity.User;
import feign.Feign;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * @author boring
 * @FeignClient(name = "microservice-provider-user") -> 要请求的服务
 * @date 2020/8/16 下午5:23
 */

/**
 * configuration 指定feign的细粒化配置信息
 * fallback feign与hystrix 整合，回退类，实现feignClient接口，重写方法为对应的回退方法
 *
 * @author boring
 */
@FeignClient(
        name = "microservice-provider-user",
//        configuration = UserFeignConfig.class,
//        configuration = UserFeignDisableHystrixConfiguration.class,
        configuration = UserFeignEnableHystrixConfiguration.class,
//        fallback = UserFeignClientFallback.class,   // fallBack类仅支持重写对应方法类实现回退接口
        fallbackFactory = UserFeignClientFallbackFactory.class)    // 通过过指定fallbackFactory,可以在create方法内返回接口匿名内部类,并获取异常信息
public interface UserFeignClient {
    /**
     * @param id id
     * @return user
     * @GetMapping("/users/{id}") -> 要请求的接口
     */
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}

/**
 * 该Feign Client的配置类，注意：
 * 1. 该类可以独立出去；
 * 2. 该类上也可添加@Configuration声明是一个配置类；
 * 但此时千万别将该放置在主应用程序上下文@ComponentScan所扫描的包中，否则，该配置将会被所有Feign Client共享，无法实现细粒度配置！
 */
class UserFeignConfig {
    // 等同于yml文件中的：feign:
    //  client:
    //    config:
    //      microservice-provider-user:
    //        loggerLevel: full
//    @Bean
//    public Logger.Level logger() {
//        return Logger.Level.FULL;
//    }
}

/**
 * 前提:yml中通过feign.hystrix.enable=true 全局启用了hystrix
 *      该配置类在局部禁用hystrix
 */
class UserFeignDisableHystrixConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}

/**
 * 前提:yml中通过feign.hystrix.enable=false 全局禁用了hystrix
 *      该配置类在局部启用hystrix
 */
class UserFeignEnableHystrixConfiguration {
    @Bean
    @Scope("prototype")
    public HystrixFeign.Builder feignBuilder() {
        return HystrixFeign.builder();
    }
}

@Component
class UserFeignClientFallback implements UserFeignClient {
    @Override
    public User findById(Long id) {
        return new User(id, "游客", "游客", 20, BigDecimal.valueOf(200));
    }
}

@Component
class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    Logger log = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                log.error("回退方法，错误信息" + throwable.getMessage());
                return new User(id, "游客", "游客", 20, BigDecimal.valueOf(200));
            }
        };
    }
}