package com.sso.repository;

import com.sso.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByToken(String token);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
