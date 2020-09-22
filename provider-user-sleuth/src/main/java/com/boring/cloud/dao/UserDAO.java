package com.boring.cloud.dao;

import com.boring.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author boring
 * @date 2020/7/31 下午4:38
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> {
}
