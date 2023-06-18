package com.example.exam.folder.repository;

import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<Singer, Long> {
}