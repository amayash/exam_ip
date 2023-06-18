package com.example.exam.folder.controller;

import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.AlbumService;
import com.example.exam.folder.service.SingService;
import com.example.exam.folder.service.SingerService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumMvcController {
    private final AlbumService albumService;
    private final SingerService singerService;
    private final SingService singService;

    public AlbumMvcController(AlbumService albumService, SingerService singerService, SingService singService) {
        this.albumService = albumService;
        this.singerService = singerService;
        this.singService = singService;
    }

    @GetMapping
    public String getAlbums(Model model) {
        model.addAttribute("albums", albumService.findAllAlbums().stream()
                .map(AlbumDto::new)
                .toList());
        return "album";
    }

    @GetMapping("/{id}")
    public String getSings(@PathVariable Long id,
                            Model model) {
        model.addAttribute("sings", singService.findSingsByAlbum(id).stream()
                .map(SingDto::new)
                .toList());
        model.addAttribute("albumId", id);
        return "album-one";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editAlbum(@PathVariable(required = false) Long id,
                            Model model) {
        if (id == null || id <= 0) {
            List<SingerDto> singers = singerService.findAllSingers().stream()
                    .map(SingerDto::new)
                    .toList();
            model.addAttribute("singers", singers);
            model.addAttribute("albumDto", new AlbumDto());
        } else {
            model.addAttribute("albumDto", new AlbumDto(albumService.findAlbum(id)));
        }
        return "album-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editAlbum(@PathVariable Long id,
                            @ModelAttribute @Valid AlbumDto albumDto,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "album-edit";
        }
        albumDto.setId(id);
        albumService.updateAlbum(albumDto);

        return "redirect:/album";
    }

    @GetMapping(value = {"/{albumId}/sing/edit", "/{albumId}/sing/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editSing(@PathVariable(required = false) Long id,
                            @PathVariable Long albumId,
                            Model model) {
        if (id == null || id <= 0) {
            SingDto singDto = new SingDto();
            singDto.setAlbumId(albumId);
            model.addAttribute("singDto", singDto);
            model.addAttribute("albumId", albumId);
        } else {
            model.addAttribute("singDto", new SingDto(singService.findSing(id)));
        }
        return "sing-edit";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveAlbum(@ModelAttribute @Valid AlbumDto albumDto) {
        albumService.addAlbum(albumDto.getSingerId(), albumDto.getName(), albumDto.getReleaseYear());
        return "redirect:/album";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return "redirect:/album";
    }
}
