package com.example.exam.folder.service;

import com.example.exam.folder.controller.SingerDto;
import com.example.exam.folder.model.Singer;
import com.example.exam.folder.model.Sing;
import com.example.exam.folder.model.User;
import com.example.exam.folder.repository.SingerRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingerService {
    private final SingerRepository singerRepository;
    private final ValidatorUtil validatorUtil;

    public SingerService(SingerRepository singerRepository, ValidatorUtil validatorUtil) {
        this.singerRepository = singerRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Singer addSinger(String name) {
        final Singer singer = new Singer(name);
        validatorUtil.validate(singer);
        return singerRepository.save(singer);
    }

    @Transactional
    public Singer findSinger(Long id) {
        final Optional<Singer> singer = singerRepository.findById(id);
        return singer.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<Singer> findAllSingers() {
        return singerRepository.findAll();
    }

    @Transactional
    public Singer updateSinger(SingerDto singer) {
        final Singer currentSinger = findSinger(singer.getId());
        currentSinger.setName(singer.getName());
        validatorUtil.validate(currentSinger);
        return singerRepository.save(currentSinger);
    }

    @Transactional
    public Singer deleteSinger(Long id) {
        final Singer currentSinger = findSinger(id);
        singerRepository.delete(currentSinger);
        return currentSinger;
    }

    @Transactional
    public void deleteAllSingers() {
        singerRepository.deleteAll();
    }
}
