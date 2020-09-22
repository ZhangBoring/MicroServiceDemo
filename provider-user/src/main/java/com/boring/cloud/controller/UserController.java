package com.boring.cloud.controller;

import com.boring.cloud.dao.UserDAO;
import com.boring.cloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author boring
 * @date 2020/7/31 下午4:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userDAO.findById(id);
    }
}
