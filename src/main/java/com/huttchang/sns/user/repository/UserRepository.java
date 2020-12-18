package com.huttchang.sns.user.repository;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.sns.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email) throws DataNotFoundException;
}
