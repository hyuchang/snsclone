package com.huttchang.sns.account.repository;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.sns.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email) throws DataNotFoundException;
}
