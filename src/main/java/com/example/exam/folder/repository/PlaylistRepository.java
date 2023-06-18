package com.example.exam.folder.repository;

import com.example.exam.folder.model.Playlist;
import com.example.exam.folder.model.Sing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUserId(Long id);
}
