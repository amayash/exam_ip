package com.example.exam.folder.repository;

import com.example.exam.folder.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
