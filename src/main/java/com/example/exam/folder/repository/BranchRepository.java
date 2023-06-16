package com.example.exam.folder.repository;

import com.example.exam.folder.model.Branch;
import com.example.exam.folder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByRepositoryId(Long id);
}
