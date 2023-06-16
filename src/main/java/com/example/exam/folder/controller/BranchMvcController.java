//package com.example.exam.folder.controller;
//
//import com.example.exam.folder.service.BranchService;
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/branch")
//public class BranchMvcController {
//    private final BranchService branchService;
//
//    public BranchMvcController(BranchService branchService) {
//        this.branchService = branchService;
//    }
//
//    @GetMapping(value = {"/edit", "/edit/{id}"})
//    public String editBranch(@PathVariable(required = false) Long id,
//                             @RequestParam("repositoryId") Long repositoryId,
//                             Model model) {
//        if (id == null || id <= 0) {
//            BranchDto branchDto = new BranchDto();
//            branchDto.setRepositoryId(repositoryId);
//            model.addAttribute("branchDto", branchDto);
//        } else {
//            model.addAttribute("branchDto", new BranchDto(branchService.findBranch(id)));
//        }
//        return "branch-edit";
//    }
//
//    @PostMapping(value = {"/", "/{id}"})
//    public String saveBranch(@PathVariable(required = false) Long id,
//                             @ModelAttribute @Valid BranchDto branchDto,
//                             BindingResult bindingResult,
//                             Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("errors", bindingResult.getAllErrors());
//            return "branch-edit";
//        }
//        if (id == null || id <= 0) {
//            branchService.addBranch(branchDto.getRepositoryId(), branchDto.getName());
//        } else {
//            branchService.updateBranch(id, branchDto.getName());
//        }
//        return "redirect:/repository/"+branchDto.getRepositoryId();
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteBranch(@PathVariable Long id,
//                               @RequestParam("repositoryId") Long repositoryId) {
//        branchService.deleteBranch(id);
//        return "redirect:/repository/"+repositoryId;
//    }
//}
