package com.example.exam.folder.controller;

import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.AlbumService;
import com.example.exam.folder.service.SingService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sing")
public class SingMvcController {
    private final SingService singService;
    private final AlbumService albumService;

    public SingMvcController(SingService singService, AlbumService albumService) {
        this.singService = singService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSings(Model model) {
        model.addAttribute("sings", singService.findAllSings().stream()
                .map(SingDto::new)
                .toList());
        return "sing";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editSing(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            List<AlbumDto> albums = albumService.findAllAlbums().stream()
                    .map(AlbumDto::new)
                    .toList();
            model.addAttribute("albums", albums);
            model.addAttribute("singDto", new SingDto());
        } else {
            model.addAttribute("singDto", new SingDto(singService.findSing(id)));
        }
        return "sing-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editSing(@PathVariable Long id,
                             @ModelAttribute @Valid SingDto singDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "sing-edit";
        }
        singDto.setId(id);
        singService.updateSing(singDto);

        return "redirect:/sing";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveSing(@ModelAttribute @Valid SingDto singDto) {
        singService.addSing(singDto.getAlbumId(), singDto.getName(),
                singDto.getTimestamp(), singDto.getDuration());
        return "redirect:/sing";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteSing(@PathVariable Long id) {
        singService.deleteSing(id);
        return "redirect:/sing";
    }
}
