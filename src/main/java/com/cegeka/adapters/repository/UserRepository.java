package com.cegeka.adapters.repository;

import com.cegeka.adapters.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
