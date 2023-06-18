package com.example.exam.folder.service;

import com.example.exam.folder.controller.AlbumDto;
import com.example.exam.folder.model.Album;
import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.Singer;
import com.example.exam.folder.repository.AlbumRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SingerService singerService;
    private final ValidatorUtil validatorUtil;

    public AlbumService(AlbumRepository albumRepository, SingerService singerService, ValidatorUtil validatorUtil) {
        this.albumRepository = albumRepository;
        this.singerService = singerService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Album addAlbum(Long singerId, String name, Integer year) {
        final Album album = new Album(name, year);
        Singer singer = singerService.findSinger(singerId);
        album.setSinger(singer);
        validatorUtil.validate(album);
        return albumRepository.save(album);
    }

    @Transactional
    public Album findAlbum(Long id) {
        final Optional<Album> album = albumRepository.findById(id);
        return album.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }
    @Transactional
    public List<Album> findAlbumsBySinger(Long id) {
        return albumRepository.findAllBySingerId(id);
    }

    @Transactional
    public Album updateAlbum(AlbumDto album) {
        final Album currentAlbum = findAlbum(album.getId());
        currentAlbum.setName(album.getName());
        validatorUtil.validate(currentAlbum);
        return albumRepository.save(currentAlbum);
    }

    @Transactional
    public Album deleteAlbum(Long id) {
        final Album currentAlbum = findAlbum(id);
        albumRepository.delete(currentAlbum);
        return currentAlbum;
    }

    @Transactional
    public void deleteAllAlbums() {
        albumRepository.deleteAll();
    }
}
