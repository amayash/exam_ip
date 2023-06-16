package com.example.exam.folder.service;

import com.example.exam.folder.model.Branch;
import com.example.exam.folder.model.Commit;
import com.example.exam.folder.model.File;
import com.example.exam.folder.repository.FileRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final CommitService commitService;
    private final ValidatorUtil validatorUtil;

    public FileService(FileRepository fileRepository, CommitService commitService, ValidatorUtil validatorUtil) {
        this.fileRepository = fileRepository;
        this.commitService = commitService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public File addFile(Long commitId, String name, String content) {
        final File file = new File(name, content);
        final Commit commit = commitService.findCommit(commitId);
        file.setCommit(commit);
        validatorUtil.validate(file);
        return fileRepository.save(file);
    }

    @Transactional(readOnly = true)
    public File findFile(Long id) {
        final Optional<File> file = fileRepository.findById(id);
        return file.orElseThrow(() -> new FileNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<File> findAllFiles() {
        return fileRepository.findAll();
    }

    @Transactional
    public File updateBranch(Long id, String name, String content) {
        final File elem = findFile(id);
        elem.setName(name);
        elem.setContent(name);
        validatorUtil.validate(elem);
        return fileRepository.save(elem);
    }

    @Transactional
    public File deleteFile(Long id) {
        final File element = findFile(id);
        fileRepository.delete(element);
        return element;
    }

    @Transactional
    public void deleteAllBranches() {
        fileRepository.deleteAll();
    }
}
