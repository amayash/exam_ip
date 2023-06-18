package com.example.exam.folder.controller;

import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.AlbumService;
import com.example.exam.folder.service.SingerService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/singer")
public class SingerMvcController {
    private final SingerService singerService;
    private final AlbumService albumService;

    public SingerMvcController(SingerService singerService, AlbumService albumService) {
        this.singerService = singerService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSingers(Model model) {
        model.addAttribute("singers", singerService.findAllSingers().stream()
                .map(SingerDto::new)
                .toList());
        return "singer";
    }

    @GetMapping("/{id}")
    public String getAlbums(@PathVariable Long id,
                             Model model) {
        model.addAttribute("albums", albumService.findAlbumsBySinger(id).stream()
                .map(AlbumDto::new)
                .toList());
        model.addAttribute("singerId", id);
        return "singer-one";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editSinger(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("singerDto", new SingerDto());
        } else {
            model.addAttribute("singerDto", new SingerDto(singerService.findSinger(id)));
        }
        return "singer-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editSinger(@PathVariable Long id,
                             @ModelAttribute @Valid SingerDto singerDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "singer-edit";
        }
        singerDto.setId(id);
        singerService.updateSinger(singerDto);

        return "redirect:/singer";
    }

    @GetMapping(value = {"/{singerId}/album/edit", "/{singerId}/album/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editAlbum(@PathVariable(required = false) Long id,
                            @PathVariable Long singerId,
                            Model model) {
        if (id == null || id <= 0) {
            AlbumDto albumDto = new AlbumDto();
            albumDto.setSingerId(singerId);
            model.addAttribute("albumDto", albumDto);
            model.addAttribute("singerId", singerId);
        } else {
            model.addAttribute("albumDto", new AlbumDto(albumService.findAlbum(id)));
        }
        return "album-edit";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveSinger(@RequestParam("name") String name) {
        singerService.addSinger(name);
        return "redirect:/singer";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteSinger(@PathVariable Long id) {
        singerService.deleteSinger(id);
        return "redirect:/singer";
    }
}
