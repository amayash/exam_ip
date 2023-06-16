package com.example.exam.folder.repository;

import com.example.exam.folder.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
