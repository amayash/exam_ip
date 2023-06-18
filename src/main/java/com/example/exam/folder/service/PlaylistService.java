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
    private final ValidatorUtil validatorUtil;

    public PlaylistService(PlaylistRepository playlistRepository, UserService userService, ValidatorUtil validatorUtil) {
        this.playlistRepository = playlistRepository;
        this.userService = userService;
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
    public Playlist addSing(Long id, Sing p) {
        Playlist e = findPlaylist(id);
        e.setSing(p);
        return playlistRepository.save(e);
    }

    @Transactional
    public Playlist deleteSing(Long id, Sing p) {
        Playlist e = findPlaylist(id);
        e.removeSing(p);
        return playlistRepository.save(e);
    }

    @Transactional
    public List<Playlist> findAllPlaylists() {
        return playlistRepository.findAll();
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
