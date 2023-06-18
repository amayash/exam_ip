package com.example.exam.folder.playlist;

import com.example.exam.folder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLoginIgnoreCase(String login);
}
