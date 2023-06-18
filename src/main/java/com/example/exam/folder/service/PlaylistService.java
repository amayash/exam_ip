package com.example.exam.folder.service;

import com.example.exam.folder.controller.PlaylistDto;
import com.example.exam.folder.model.Playlist;
import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.User;
import com.example.exam.folder.repository.PlaylistRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final SingService singService;
    private final ValidatorUtil validatorUtil;

    public PlaylistService(PlaylistRepository playlistRepository, UserService userService, SingService singService, ValidatorUtil validatorUtil) {
        this.playlistRepository = playlistRepository;
        this.userService = userService;
        this.singService = singService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Playlist addPlaylist(Long userId, String name) {
        final Playlist playlist = new Playlist(name);
        final User user = userService.findUser(userId);
        playlist.setUser(user);
        validatorUtil.validate(playlist);
        return playlistRepository.save(playlist);
    }

    @Transactional
    public Playlist findPlaylist(Long id) {
        final Optional<Playlist> playlist = playlistRepository.findById(id);
        return playlist.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Playlist addSing(Long id, Long singId) {
        Playlist e = findPlaylist(id);
        Sing sing = singService.findSing(singId);
        e.setSing(sing);
        return playlistRepository.save(e);
    }

    @Transactional
    public Playlist deleteSing(Long id, Long singId) {
        Playlist e = findPlaylist(id);
        Sing sing = singService.findSing(singId);
        e.removeSing(sing);
        return playlistRepository.save(e);
    }

    @Transactional
    public List<Playlist> findAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Transactional
    public List<Playlist> findAllPlaylistsByUser(Long id) {
        return playlistRepository.findAllByUserId(id);
    }

    @Transactional
    public Playlist updatePlaylist(PlaylistDto playlist) {
        final Playlist currentPlaylist = findPlaylist(playlist.getId());
        currentPlaylist.setName(playlist.getName());
        validatorUtil.validate(currentPlaylist);
        return playlistRepository.save(currentPlaylist);
    }

    @Transactional
    public Playlist deletePlaylist(Long id) {
        final Playlist currentPlaylist = findPlaylist(id);
        playlistRepository.delete(currentPlaylist);
        return currentPlaylist;
    }

    @Transactional
    public void deleteAllPlaylists() {
        playlistRepository.deleteAll();
    }
}
