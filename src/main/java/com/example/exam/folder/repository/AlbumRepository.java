package com.example.exam.folder.repository;

import com.example.exam.folder.model.Album;
import com.example.exam.folder.model.Sing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
