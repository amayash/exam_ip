package com.example.exam.folder.repository;

import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingRepository extends JpaRepository<Sing, Long> {
}