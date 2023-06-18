package com.example.exam.folder.service;

import com.example.exam.folder.controller.SingDto;
import com.example.exam.folder.model.Album;
import com.example.exam.folder.model.Sing;
import com.example.exam.folder.repository.SingRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SingService {
    private final SingRepository singRepository;
    private final AlbumService albumService;
    private final ValidatorUtil validatorUtil;

    public SingService(SingRepository singRepository, AlbumService albumService, ValidatorUtil validatorUtil) {
        this.singRepository = singRepository;
        this.albumService = albumService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Sing addSing(Long albumId, String name, LocalDateTime date, Integer duration) {
        final Sing sing = new Sing(name, duration, date);
        final Album album = albumService.findAlbum(albumId);
        sing.setAlbum(album);
        validatorUtil.validate(sing);
        return singRepository.save(sing);
    }

    @Transactional
    public Sing findSing(Long id) {
        final Optional<Sing> sing = singRepository.findById(id);
        return sing.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<Sing> findAllSings() {
        return singRepository.findAll();
    }

    @Transactional
    public List<Sing> findSingsByAlbum(Long id) {
        return singRepository.findAllByAlbumId(id);
    }

    @Transactional
    public List<Sing> findSingsByPlaylist(Long id) {
        return singRepository.findAllByPlaylistsId(id);
    }

    @Transactional
    public Sing updateSing(SingDto sing) {
        final Sing currentSing = findSing(sing.getId());
        currentSing.setName(sing.getName());
        validatorUtil.validate(currentSing);
        return singRepository.save(currentSing);
    }

    @Transactional
    public Sing deleteSing(Long id) {
        final Sing currentSing = findSing(id);
        singRepository.delete(currentSing);
        return currentSing;
    }

    @Transactional
    public void deleteAllSings() {
        singRepository.deleteAll();
    }
}
