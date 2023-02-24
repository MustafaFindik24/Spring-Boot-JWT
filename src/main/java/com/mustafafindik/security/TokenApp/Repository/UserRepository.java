package com.mustafafindik.security.TokenApp.Repository;

import com.mustafafindik.security.TokenApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
