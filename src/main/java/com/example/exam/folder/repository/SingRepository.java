package com.example.exam.folder.repository;

import com.example.exam.folder.model.Album;
import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SingRepository extends JpaRepository<Sing, Long> {
    List<Sing> findAllByAlbumId(Long id);
    List<Sing> findAllByPlaylistsId(Long id);
}