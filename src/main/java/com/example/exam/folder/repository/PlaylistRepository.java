package com.example.exam.folder.repository;

import com.example.exam.folder.model.Playlist;
import com.example.exam.folder.model.Sing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
