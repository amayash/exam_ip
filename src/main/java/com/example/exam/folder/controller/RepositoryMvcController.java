package com.example.exam.folder.controller;

import com.example.exam.folder.model.Repository;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.BranchService;
import com.example.exam.folder.service.RepositoryService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/repository")
public class RepositoryMvcController {
    private final RepositoryService repositoryService;
    private final UserService userService;
    private final BranchService branchService;

    public RepositoryMvcController(RepositoryService repositoryService, UserService userService, BranchService branchService) {
        this.repositoryService = repositoryService;
        this.userService = userService;
        this.branchService = branchService;
    }

    @GetMapping
    public String getRepositories(Model model,
                                  Principal principal) {
        User user = userService.findByLogin(principal.getName());
        if (user.getRole() == UserRole.USER)
            model.addAttribute("repositories",
                    repositoryService.findAllRepositories().stream()
                            .map(RepositoryDto::new)
                            .filter(repositoryDto -> Objects.equals(repositoryDto.getUserId(),
                                    user.getId()))
                            .toList());
        else
            model.addAttribute("repositories",
                    repositoryService.findAllRepositories().stream()
                            .map(RepositoryDto::new)
                            .toList());
        return "repository";
    }

    @GetMapping(value = {"/{id}"})
    public String getBranches(@PathVariable(required = false) Long id,
                              Model model,
                              Principal principal) {
        Repository repository = repositoryService.findRepository(id);
        User user = userService.findByLogin(principal.getName());
        if (!Objects.equals(repository.getUser().getId(), user.getId()))
            return "/repository";
        model.addAttribute("branches",
                repository.getBranches().stream().map(BranchDto::new)
                        .toList());
        model.addAttribute("repository", repository);
        return "branch";
    }

    @GetMapping(value = {"/{repositoryId}/edit", "/{repositoryId}/edit/{id}"})
    public String editBranch(@PathVariable Long repositoryId,
                             @PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            BranchDto branchDto = new BranchDto();
            branchDto.setRepositoryId(repositoryId);
            model.addAttribute("branchDto", branchDto);
        } else {
            model.addAttribute("branchDto", new BranchDto(branchService.findBranch(id)));
        }
        return "branch-edit";
    }

    @PostMapping(value = {"/{repositoryId}/branch/", "/{repositoryId}/branch/{id}"})
    public String saveBranch(@PathVariable Long repositoryId,
                             @PathVariable(required = false) Long id,
                             @ModelAttribute @Valid BranchDto branchDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "branch-edit";
        }
        if (id == null || id <= 0) {
            branchService.addBranch(repositoryId, branchDto.getName());
        } else {
            branchService.updateBranch(id, branchDto.getName());
        }
        return "redirect:/repository/"+repositoryId;
    }

    @PostMapping("/{repositoryId}/delete/{id}")
    public String deleteBranch(@PathVariable Long id,
                               @PathVariable Long repositoryId) {
        branchService.deleteBranch(id);
        return "redirect:/repository/"+repositoryId;
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editRepository(@PathVariable(required = false) Long id,
                                 Model model,
                                 Principal principal) {
        if (id == null || id <= 0) {
            model.addAttribute("repositoryDto", new RepositoryDto());
        } else {
            Repository repository = repositoryService.findRepository(id);
            User user = userService.findByLogin(principal.getName());
            if (!Objects.equals(repository.getUser().getId(), user.getId()))
                return "redirect:/repository";
            model.addAttribute("repositoryId", id);
            model.addAttribute("repositoryDto", repository);
        }
        return "repository-edit";
    }

    @PostMapping(value = "/")
    public String saveRepository(@ModelAttribute @Valid RepositoryDto repositoryDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        Long userId = userService.findByLogin(principal.getName()).getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "repository-edit";
        }
        repositoryService.addRepository(userId, repositoryDto.getName(), repositoryDto.getDescription());
        return "redirect:/repository";
    }

    @PostMapping(value = {"/{id}"})
    public String editRepository(@PathVariable Long id,
                                 @ModelAttribute @Valid RepositoryDto repositoryDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "repository-edit";
        }
        Repository repository = repositoryService.findRepository(id);
        User user = userService.findByLogin(principal.getName());
        if (!Objects.equals(repository.getUser().getId(), user.getId()))
            return "/repository";
        repositoryService.updateRepository(id, repositoryDto.getName(), repositoryDto.getDescription());
        return "redirect:/repository";
    }

    @PostMapping("/delete/{id}")
    public String deleteRepository(@PathVariable Long id,
                                   Principal principal) {
        Repository repository = repositoryService.findRepository(id);
        User user = userService.findByLogin(principal.getName());
        if (!Objects.equals(repository.getUser().getId(), user.getId()) && user.getRole() == UserRole.USER)
            return "redirect:/repository";
        repositoryService.deleteRepository(id);
        return "redirect:/repository";
    }
}
