package com.example.exam.folder.service;

import com.example.exam.folder.model.Branch;
import com.example.exam.folder.model.Commit;
import com.example.exam.folder.model.Repository;
import com.example.exam.folder.model.User;
import com.example.exam.folder.repository.BranchRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final RepositoryService repositoryService;
    private final ValidatorUtil validatorUtil;

    public BranchService(BranchRepository branchRepository, RepositoryService repositoryService, ValidatorUtil validatorUtil) {
        this.branchRepository = branchRepository;
        this.repositoryService = repositoryService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Branch addBranch(Long repositoryId, String name) {
        final Branch branch = new Branch(name);
        final Repository repository = repositoryService.findRepository(repositoryId);
        branch.setRepository(repository);
        validatorUtil.validate(branch);
        return branchRepository.save(branch);
    }

    @Transactional(readOnly = true)
    public Branch findBranch(Long id) {
        final Optional<Branch> branch = branchRepository.findById(id);
        return branch.orElseThrow(() -> new BranchNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Branch> findAllBranches() {
        return branchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Branch> findByRepository(Long id) {
        return branchRepository.findAllByRepositoryId(id);
    }

    @Transactional
    public Branch updateBranch(Long id, String name) {
        final Branch elem = findBranch(id);
        elem.setName(name);
        validatorUtil.validate(elem);
        return branchRepository.save(elem);
    }

    @Transactional
    public Branch deleteBranch(Long id) {
        final Branch element = findBranch(id);
        branchRepository.delete(element);
        return element;
    }

    @Transactional
    public void deleteAllBranches() {
        branchRepository.deleteAll();
    }
}
