package com.nick.db.starter.dbstarter.repository;

import com.nick.db.starter.dbstarter.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
}
