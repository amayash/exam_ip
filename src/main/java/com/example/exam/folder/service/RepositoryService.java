package com.example.exam.folder.service;

import com.example.exam.folder.model.Repository;
import com.example.exam.folder.model.User;
import com.example.exam.folder.repository.RepositoryRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final UserService userService;
    private final ValidatorUtil validatorUtil;

    public RepositoryService(RepositoryRepository repositoryRepository, UserService userService, ValidatorUtil validatorUtil) {
        this.repositoryRepository = repositoryRepository;
        this.userService = userService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Repository addRepository(Long userId, String name, String description) {
        final Repository repository = new Repository(name, description);
        final User user = userService.findUser(userId);
        repository.setUser(user);
        validatorUtil.validate(repository);
        return repositoryRepository.save(repository);
    }

    @Transactional
    public Repository findRepository(Long id) {
        final Optional<Repository> repository = repositoryRepository.findById(id);
        return repository.orElseThrow(() -> new RepositoryNotFoundException(id));
    }

    @Transactional
    public List<Repository> findAllRepositories() {
        return repositoryRepository.findAll();
    }

    @Transactional
    public Repository updateRepository(Long id, String name, String description) {
        final Repository currentRep = findRepository(id);
        currentRep.setName(name);
        currentRep.setDescription(description);
        validatorUtil.validate(currentRep);
        return repositoryRepository.save(currentRep);
    }

    @Transactional
    public Repository deleteRepository(Long id) {
        final Repository currRepository = findRepository(id);
        repositoryRepository.delete(currRepository);
        return currRepository;
    }
}
