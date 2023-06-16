package com.example.exam.folder.service;

import com.example.exam.folder.model.Branch;
import com.example.exam.folder.model.Commit;
import com.example.exam.folder.repository.CommitRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CommitService {
    private final CommitRepository commitRepository;
    private final BranchService branchService;
    private final ValidatorUtil validatorUtil;

    public CommitService(CommitRepository commitRepository, BranchService branchService, ValidatorUtil validatorUtil) {
        this.commitRepository = commitRepository;
        this.branchService = branchService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Commit addCommit(Long branchId, String name) {
        final Commit commit = new Commit(name);
        final Branch branch = branchService.findBranch(branchId);
        commit.setBranch(branch);
        validatorUtil.validate(commit);
        return commitRepository.save(commit);
    }

    @Transactional(readOnly = true)
    public Commit findCommit(Long id) {
        final Optional<Commit> commit = commitRepository.findById(id);
        return commit.orElseThrow(() -> new CommitNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Commit> findAllCommits() {
        return commitRepository.findAll();
    }

    @Transactional
    public Commit updateCommit(Long id, String name) {
        final Commit elem = findCommit(id);
        elem.setName(name);
        validatorUtil.validate(elem);
        return commitRepository.save(elem);
    }

    @Transactional
    public Commit deleteCommit(Long id) {
        final Commit element = findCommit(id);
        commitRepository.delete(element);
        return element;
    }

    @Transactional
    public void deleteAllCommits() {
        commitRepository.deleteAll();
    }
}
